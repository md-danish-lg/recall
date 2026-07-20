from fastapi import FastAPI, HTTPException

app = FastAPI()


@app.get("/")
async def check_status():
    return {"status": "ok"}