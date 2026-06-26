from fastapi import FastAPI
from pydantic import BaseModel, Field
from src.services.GroqService import GroqService
import json
import re


import py_eureka_client.eureka_client as eureka_client
from contextlib import asynccontextmanager

@asynccontextmanager
async def lifespan(app: FastAPI):
    await eureka_client.init_async(
        eureka_server="http://discovery-service:8761/eureka/",
        app_name="recommandation-service",
        instance_port=8083,
        instance_ip="recommandation-service",
    )
    yield
    await eureka_client.stop_async()

app = FastAPI(lifespan=lifespan)
groq_service = GroqService()


class ProjectInfo(BaseModel):
    project_name: str
    description: str
    problem_statement: str | None = None
    target_users: str
    backend_type: str


class CoreFeatures(BaseModel):
    authentication: bool = False
    user_roles: bool = False
    crud_operations: bool = False
    file_storage: bool = False
    logging_system: bool = False
    api_docs: bool = False


class AdvancedFeatures(BaseModel):
    real_time: bool = False
    background_jobs: bool = False
    scheduled_tasks: bool = False
    ai_processing: bool = False


class FunctionalRequirements(BaseModel):
    core_features: CoreFeatures
    advanced_features: AdvancedFeatures = AdvancedFeatures()
    use_cases: list[str] = Field(default_factory=list)


class NonFunctionalRequirements(BaseModel):
    expected_users: str
    api_performance: str
    scalability: str = "plan_for_later"
    high_availability: bool = False
    security_level: str
    data_sensitivity: str


class ArchitecturePreferences(BaseModel):
    use_ai_recommendation: bool = False
    api_style: str = "rest"


class TechnologyPreferences(BaseModel):
    backend_framework: str | None = "ai_decides"
    orm_data_layer: str | None = "ai_decides"
    database: str | None = "ai_decides"
    priority: str = "simplicity"


class BudgetDeployment(BaseModel):
    budget_level: str
    deployment_type: str = "cloud"
    environment: str = "dev_production"
    docker: bool = True
    cicd: bool = False


class ProjectRequest(BaseModel):
    project_info: ProjectInfo
    functional_requirements: FunctionalRequirements
    non_functional_requirements: NonFunctionalRequirements
    architecture_preferences: ArchitecturePreferences = ArchitecturePreferences()
    technology_preferences: TechnologyPreferences = TechnologyPreferences()
    budget_deployment: BudgetDeployment


def safe_parse_llm_json(raw_text: str) -> dict:
    """
    Parsing défensif : essaie un json.loads() direct, puis en dernier recours
    extrait le plus grand bloc {...} présent dans le texte.
    """
    try:
        return json.loads(raw_text)
    except json.JSONDecodeError:
        pass

    match = re.search(r"\{.*\}", raw_text, re.DOTALL)
    if match:
        try:
            return json.loads(match.group(0))
        except json.JSONDecodeError:
            pass

    return {
        "status": "error",
        "error": "llm_invalid_response",
        "message": "Le LLM n'a pas renvoyé un JSON valide.",
        "raw": raw_text,
    }


@app.post("/generate-architecture")
def generate_architecture(data: ProjectRequest):
    user_input = data.model_dump_json()
    result = groq_service.chat(user_input)
    return safe_parse_llm_json(result)