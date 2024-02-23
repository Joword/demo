<template>
    <div class="article-list">
        <div class="art-sty">
            <div class="sty-hcom sty-head1">
                <span>Article</span>
            </div>
            <div class="sty-hcom sty-head2">
                <span>Abstract</span>
            </div>
            <div class="sty-hcom sty-head3">
                <span>ACMG Criteria</span>
            </div>
        </div>
        <div class="art-sty" v-for="item in articleDataInfo" :key="item.pmid">
            <div class="sty-com sty-1">
                <div class="rate">
                    <div class="rate-name">Relevance: </div>
                    <div class="rate-display">
                        <span style="background-color: #24D6CA;" v-for="i in Number(item.relevance ?? 0)" :key="i"></span>
                        <span style="border: 1px solid #24D6CA;" v-for="t in (5 - Number(item.relevance ?? 0))" :key="t"></span>
                        <!-- <span></span>
                        <span></span>
                        <span></span> -->
                    </div>
                </div>
                <div class="explain">
                    <p @click="toChatPaper(item.pmid, item.pmcid)">{{ item.title }}</p>
                </div>
                <div class="origin">
                    <span>{{ item.firstAuthor }}</span>&nbsp;&nbsp;<a :href="'https://pubmed.ncbi.nlm.nih.gov/' + item.pmid" target="_blank">PMID: {{ item.pmid }}</a>
                </div>
                <div class="author-time">
                    <span> {{ item.date }} | Journal: {{ item.journal }} | {{ item.citationNum }}</span>
                </div>
            </div>
            <div class="sty-com sty-2">
                <div class="absract-desc">
                    <n-ellipsis :line-clamp="6" expand-trigger="click" :tooltip="false">
                        {{ item.abstractText }}
                        <!-- <template #tooltip>
                            <div style="width: 800px;">
                                {{ item.abstractText }}
                            </div>
                        </template> -->
                    </n-ellipsis>
                </div>
            </div>
            <div class="sty-com sty-3">
                <div class="acmg-btn">
                    <div class="acmg-card" :style="item.moreCard ? 'height: auto' : 'height: 115px'">
                        <CriteriaCard v-for="v in item.evidences" :info="{key: v.name, value: v.strength}" class="card-sty" />
                        <!-- <CriteriaCard :info="test1" class="card-sty"/> -->
                    </div>
                </div>
                <div class="more-card-icon" v-if="item.evidences.length > 2">
                    <n-icon class="more-icon" v-if="!item.moreCard" :component="CaretDownOutline" @click="() => item.moreCard = true" />
                    <n-icon class="more-icon" v-else :component="CaretUpOutline" @click="() => item.moreCard = false" />
                </div>
            </div>
        </div>
        <div class="art-add">
            <span class="add-label">Add Article</span>
            <n-input placeholder="Pmid" v-model:value="inputValue" class="add-input">
                <template #suffix>
                    <n-icon class="add-icon" @click="getPublicationInfor" :component="Add" />
                </template>
            </n-input>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { h, ref, onMounted } from 'vue';
    // import { useI18n } from "vue-i18n";
    import { NIcon, NInput } from "naive-ui";
    import { getPublicationList } from "@/api/request";
    import { Dialog, Message } from "@/utils/utils";
    import { Add, CaretDownOutline, CaretUpOutline } from "@vicons/ionicons5";
    import { useRoute, useRouter } from "vue-router";

    import CriteriaCard from "./criteriaCard.vue";
    import PublicationInfor from './publicationInfor.vue';
    const route = useRoute();
    const router = useRouter();
    // const { t } = useI18n();
    const inputValue = ref();
    // const moreCard = ref(false)
    let articleDataInfo = ref<any[]>([]);
    const pmidSet = ref<[]>([]);
    const getArticleInfo = () => {
        getPublicationList(route.params.variantId).then(res=> {
            if(res.data.code === 1000) {
                if(!res.data.data || res.data.data.length === 0) return;
                articleDataInfo.value = res.data.data.map((v: any)=> {
                    let evidences = v.evidences.filter((t: any)=> {
                        return t.strength !== null && t.strength !== 'Unmet'
                    })
                    return { moreCard: false, ...v, evidences: evidences }
                })
                console.log(articleDataInfo.value)
                articleDataInfo.value.sort((a,b)=> b.evidences.length - a.evidences.length);
                pmidSet.value = res.data.data.map((v: any)=> {
                    return v.pmid;
                })
            }
        })
    }
    const getPublicationInfor = () => {
        if(!inputValue.value) {
            Message.warning('Please input pmid');
            return
        }
        const d = Dialog.info({
            title: ()=> h('div', {
                style: { width: '100%', textAlign: 'center', fontWeight: 'bolder', fontSize: '20px' }
            }, { default: () => "Publication information" }),
            showIcon: false,
            style: { 'width': '50%', 'border-radius': '10px' },
            content: ()=> h(PublicationInfor, { info: inputValue.value, variantId: route.params.variantId, pmidSet: pmidSet, from: '', onGetInfor(data: any) {
                data.moreCard = false;
                articleDataInfo.value.push(data);
                articleDataInfo.value.sort((a,b)=> b.evidences.length - a.evidences.length);
                d.destroy();
            }})
        })
    }
    const toChatPaper = (pmid: string, pmcid: string) => {
        router.push({path: '/paper', query: { pmid, pmcid }})
    }
    onMounted(()=> {
        getArticleInfo();
    })
</script>

<style lang="scss" scoped>
    .article-list {
        padding: 0 10px;
        .art-sty {
            display: flex;
            border-bottom: 1px solid #CCCCCC;
            text-align: left;
            flex-wrap: nowrap;
            .sty-hcom {
                color: #808080;
                font-size: 14px;
                font-weight: bolder;
            }
            .sty-head1 {
                width: 30%;
                padding-right: 40px;
            }
            .sty-head2 {
                width: 50%;
                padding-right: 20px;
            }
            .sty-head3 {
                width: 20%;
                text-align: center;
            }
            .sty-com {
                font-size: 12px;
            }
            .sty-1 {
                width: 30%;
                color: #383838;
                padding: 10px 40px 10px 0;
                .rate {
                    display: flex;
                    align-items: center;
                    .rate-display {
                        margin-left: 10px;
                        margin-top: 3px;
                        span {
                            display: inline-block;
                            width: 12px;
                            height: 12px;
                            // background-color: #24D6CA;
                            margin-right: 4px;
                            box-sizing: border-box;
                        }
                    }
                }
                .explain {
                    color: #15756F;
                    font-weight: bold;
                    line-height: 1.5;
                    font-size: 13px;
                    p {
                        cursor: pointer;
                        &:hover {
                            text-decoration: underline;
                        }
                    }
                }
                .origin {
                    line-height: 1.5;
                    margin-top: 8px;
                    a {
                        text-decoration: underline;
                        color: #0954AB;
                        font-weight: bolder;
                    }
                }
                .author-time {
                    line-height: 1.5;
                }
            }
            .sty-2 {
                width: 50%;
                padding: 10px 20px 10px 0;
                .absract-desc {
                    color: #383838;
                }
            }
            .sty-3 {
                width: 20%;
                .acmg-btn {
                    display: flex;
                    // flex-wrap: wrap;
                    justify-content: center;
                    .acmg-card {
                        // height: 120px;
                        overflow: hidden;
                        // margin: 10px auto;
                        .card-sty {
                            margin-top: 8px;
                            // margin-left: 8px;
                        }
                    }
                }
                .more-card-icon {
                    text-align: center;
                    .more-icon {
                        font-size: 24px;
                        color: rgba(9,84,171, .5);
                        &:hover {
                            color: rgba(9,84,171, 1);;
                        }
                    }
                }
            }
        }
        .art-add {
            width: 30%;
            display: flex;
            align-items: center;
            margin-top: 16px;
            margin-bottom: 16px;
            .add-label {
                font-size: 14px;
                font-weight: bolder;
                color: #383838;
                margin-right: 10px
            }
            .add-input {
                width: calc(100% - 110px);
            }
            .add-icon {
                color: #000;
            }
        }
    }
</style>