"""
@author Joword 
@time: 2024/02/01 15:10:10
@version: 1.0.0
@description: ACMG服务层
"""
from typing import List
from ..commons.utils import *

from backend.schemas.mongo import collections
from ..commons.utils import Criterias
from ..models.types import VariantInfo, CriteriaInfo, ChatpaperShowingInfo

from ..models.request.type import VariantArgs, CriteriaArgs, ChatpaperShowing

def handler_acmg_info(req_data: VariantArgs) -> VariantInfo:
    variant_info_all = collections['aigidb_decoding'].find_one({"variant_id":req_data.variant_id})
    variant_info: VariantInfo = VariantInfo.parse_obj({convert_camel(i):variant_info_all[i] for i in variant_info_all})
    variant_info.phgvs = variant_info_all['p_hgvs']
    variant_info.ghgvs = variant_info_all['g_hgvs']
    variant_info.chgvs = variant_info_all['c_hgvs']
    variant_info.criteria = variant_info_all['acmg_classification']['userId_'+req_data.user_id]['met_acmg']
    variant_info.classification = variant_info_all['acmg_classification']['userId_'+req_data.user_id]['pathogenicity']
    variant_info.clinvarObj = variant_info_all['clinvarInfo']
    variant_info.evidences = variant_info_all['acmg_classification']['userId_'+req_data.user_id]['acmg']
    return variant_info

def handler_acmg_criteria(data: CriteriaArgs) -> List:
    criteria_list: List = []
    criteria_info_all = collections['aigidb_decoding'].find_one({"variant_id":data.vd})['acmg_classification']['userId_'+data.u]['acmg']
    for k,v in criteria_info_all.items():
        criteria_info = CriteriaInfo()
        criteria_info.rule = k.upper()
        criteria_info.strength = v['strength']
        if k in Criterias.auto():
            if k in Criterias.population():
                criteria_info.category = "Population"
            elif k in Criterias.computation():
                criteria_info.category = "Computation"
            elif k in Criterias.case():
                criteria_info.category = "Case/Segregation"
            criteria_info.evidence = v['description']
            criteria_info.summary = v['description']
            criteria_info.comment = v['comment'] if 'comment' in v else None
            criteria_info.homozygot = v['homozygote'] if 'homozygote' in v else None
            criteria_info.af = v['popmax'] if 'popmax' in v else None
            criteria_info.repeatedRegion = v['repetitive_region'] if 'repetitive_region' in v else None
            criteria_info.revelScore = v['revel_score'] if 'revel_score' in v else None
            criteria_info.impactSplicing = v['splicing'] if 'splicing' in v else None
        elif k in Criterias.semiauto():
            criteria_info.evidences = v['evidences']
        criteria_list.append(criteria_info)
    return criteria_list

def handler_chat_info(arg: ChatpaperShowing) -> ChatpaperShowingInfo:
    chat_info_all = collections['aigidb_decoding'].find_one({"variant_id":arg.variantId})
    chat_info: ChatpaperShowingInfo = ChatpaperShowingInfo.parse_obj({i:chat_info_all[i] for i in chat_info_all})
    chat_info.acmgs = chat_info_all['acmg_classification']['userId_'+arg.userId]['acmg']
    chat_info.met_acmg = chat_info_all['acmg_classification']['userId_'+arg.userId]['met_acmg']
    chat_info.pathogenicity = chat_info_all['acmg_classification']['userId_'+arg.userId]['pathogenicity']
    return chat_info
