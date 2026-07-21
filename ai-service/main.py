from fastapi import FastAPI, UploadFile, File, Form, HTTPException
from pypdf import PdfReader
import chromadb
from langchain_text_splitters import RecursiveCharacterTextSplitter
from chromadb.utils.embedding_functions import SentenceTransformerEmbeddingFunction

app = FastAPI()
chroma = chromadb.PersistentClient(path="./chroma")
collection = chroma.get_or_create_collection(
    name="doc_chunks",
    embedding_function=SentenceTransformerEmbeddingFunction(model_name="all-MiniLM-L6-v2")

)

@app.get("/")
async def check_status():
    return {"status": "ok"}


@app.post("/embed")
async def embed_file(document_id: str=Form(...), file: UploadFile = File(...)):
    name = (file.filename or "").lower()



    if name.endswith(".pdf"):
        reader=PdfReader(file.file)
        raw_text = "\n".join(page.extract_text() or "" for page in reader.pages)
    elif name.endswith('.md'):
        raw_text = (await file.read()).decode("utf-8", errors="ignore")
    elif name.endswith('.txt'):
        raw_text = (await file.read()).decode("utf-8", errors="ignore").strip()
    else: 
        raise HTTPException(400, "Supported Types: .pdf, .txt, .md")
    
    
    if not raw_text:
        raise HTTPException(400, "No extractable text")
    
    splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
    chunks = splitter.split_text(raw_text)

    ids=[f"{document_id}:{i}" for i in range(len(chunks))]
    metadatas = [{"document_id":document_id, "chunk_index": i, "filename": file.filename} for i in range(len(chunks))]
    collection.add(ids=ids, documents=chunks, metadatas=metadatas)

    return {"success": True, "document_id": document_id, "chunk_count": len(chunks)}
    





    