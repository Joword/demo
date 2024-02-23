"""
@author Joword 
@time: 2024/02/02 18:25:53
@version: 
@description: 
"""
from tortoise import fields
from tortoise.models import Model
from tortoise.contrib.pydantic import pydantic_model_creator


class AbstractBaseModel(Model):

    id = fields.IntField(pk=True)
    created_time = fields.DatetimeField(null=True, auto_now_add=True)
    update_time = fields.DatetimeField(null=True, auto_now=True)

    class Meta:
        abstract = True

class NamedEntityRecognition(Model):
    id = fields.IntField(pk=True)
    pmid = fields.CharField(255, null=True)
    type = fields.CharField(255, null=True)
    concerptId = fields.TextField()
    mentions = fields.TextField()
    resources = fields.TextField()
    class Meta:
        table = "ner_variant"
    
    def __str__(self):
        return self.pmid

nerVariant = pydantic_model_creator(NamedEntityRecognition,name="NamedEntityRecognition")