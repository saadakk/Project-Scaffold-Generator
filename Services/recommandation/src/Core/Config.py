import hvac
import os

VAULT_ADDR = os.getenv("VAULT_ADDR", "http://vault:8200")
VAULT_TOKEN = os.getenv("VAULT_TOKEN", "myroot")

client = hvac.Client(url=VAULT_ADDR, token=VAULT_TOKEN)
secret = client.secrets.kv.v2.read_secret_version(path="recommandation-service")
data = secret["data"]["data"]

GROQ_API_KEY = data.get("GROQ_API_KEY")
Model = os.getenv("MODEL", "llama-3.3-70b-versatile")