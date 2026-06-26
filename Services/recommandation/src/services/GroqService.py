from groq import Groq
from src.Core.Config import GROQ_API_KEY, Model
from src.prompts.system_message import system_message
import json


class GroqService:
    def __init__(self):
        self.client = Groq(api_key=GROQ_API_KEY)
        self.model = Model

    def chat(self, user_input):
        response = self.client.chat.completions.create(
            model=self.model,
            temperature=0.2,
            # CRITICAL: force Groq à renvoyer du JSON pur, sans balises ```
            # C'est ce qui manquait et causait le fallback {"raw": "...```...```"}
            response_format={"type": "json_object"},
            messages=[
                {"role": "system", "content": system_message},
                {"role": "user", "content": json.dumps(user_input)}
            ]
        )

        return response.choices[0].message.content
