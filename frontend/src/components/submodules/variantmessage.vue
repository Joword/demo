<template>
    <!-- <div style="line-height:50px;">
        <span style="font-weight:bold;font-s">Variant information</span>
    </div> -->
    <div class="variant-message">
        <div class="variant-info">
            <div class="info-title">
                <h3>Variant information</h3>
            </div>
            <div class="info-img">
                <div><img src="https://img.js.design/assets/img/64e584a63d7bcf1097c7a232.png#5dc901d151cae508cbb01963854d4939" alt=""></div>
            </div>
            <div class="info-display">
                <div v-for="item in variantData" :key="item.name">
                    <p class="li-name">{{ item.name }}</p>
                    <p class="li-value">{{ item.value }}</p>
                </div>
            </div>
            <div class="info-desc">
                <div class="desc-para">
                  <p style="text-align:left;" v-html="variantInfo.variantBasicInfo.chgvsDetail"></p>
                  <p style="text-align: left;" v-html="variantInfo.variantBasicInfo.phgvsDetail"></p>
                </div>
                <div></div>
            </div>
        </div>
        <div class="variant-transcripts">
            <div class="transcripts-title">
                <h3>Transcripts</h3>
            </div>
            <div class="transcripts-type">
                <div class="type-title">
                    <span>RefSeq transcripts</span> 
                    <span>(Version:2.10)</span>
                </div>
                <div class="type-content"></div>
            </div>
            <div class="transcripts-type">
                <div class="type-title">
                    <span>RefSeq transcripts</span> 
                    <span>(Version:2.10)</span>
                </div>
                <div class="type-content"></div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
// import {useRoute} from "vue-router";
import {reactive, onMounted, watch} from 'vue';
import {useInformation} from "@/store";
// import {getVariantId} from "@/api/request";

// const route = useRoute();
const props = defineProps(['info']);
const variantInfo = useInformation();
const variantData = reactive([{
        name: 'Chromosome',
      value: ''
    },{
        name: 'Position',
      value: ''
    },{
        name: 'REF',
      value: ''
    },{
        name: 'ALT',
      value: ''
    },{
      name: 'Gene',
      value: ''
    },
      {
        name: 'RS ID',
        value: ''
      },
      {
        name: 'Transcript',
        value: ''
      },
      {
        name: 'c.HGVS',
        value: ''
      },
      {
        name: 'p.HGVS',
        value: ''
      },
      {
        name: 'Type',
        value: ''
      },
      {
        name: 'Cytoband',
        value: ''
    }]);
const makeData = () => {
    variantData.forEach((e: any) => {
        switch (e.name) {
            case "Chromosome":
                e.value = "Chr" + props.info.variantId.split("-")[0];
                break;
            case "Position":
                e.value = props.info.variantId.split("-")[1];
                break;
                // @ts-ignore
            case "REF":
                e.value = props.info.variantId.split("-")[2];
                break;
                // @ts-ignore
            case "ALT":
                e.value = props.info.variantId.split("-")[3];
                break;
            case "Type":
                e.value = "SNV";
                break;
            case "Cytoband":
                e.value = props.info.cytoband;
                break;
            case "Transcript":
                e.value = props.info.transcript;
                break;
            case "c.HGVS":
                e.value = props.info.chgvs;
                break;
            case "p.HGVS":
                e.value = props.info.phgvs;
                break;
            case "RS ID":
                e.value = props.info.rsId;
                break;
            case "Gene":
                e.value = props.info.gene;
                break;
        }
    });  
}

onMounted(() => {
    // if (variantInfo.variantBasicInfo.variantId === "") {
    //     const data = new FormData();
    //     data.append("vd", route.params.variantId.toString());
    //     getVariantId(data).then(res => {
    //         if (res.data.code === 1000) {
                // variantInfo.variantBasicInfo.gene = res.data.data.gene;
                // variantInfo.variantBasicInfo.variantId = res.data.data.variantId;
                // variantInfo.variantBasicInfo.transcript = res.data.data.transcript;
                // variantInfo.variantBasicInfo.classification = res.data.data.classification;
                // variantInfo.variantBasicInfo.criteria = res.data.data.criteria;
                // variantInfo.variantBasicInfo.chgvs = res.data.data.chgvs.split(":")[1];
                // variantInfo.variantBasicInfo.phgvs = res.data.data.phgvs.split(":")[1];
                // variantInfo.variantBasicInfo.rsId = res.data.data.rsId;
                // variantInfo.variantBasicInfo.cytoband = res.data.data.cytoband;
                // variantInfo.variantBasicInfo.chgvsDetail = res.data.data.chgvsSummary;
                // variantInfo.variantBasicInfo.phgvsDetail = res.data.data.phgvsSummary;
    //             makeData()
    //         }
    //     });
    // } else {
    //     makeData()
    // }
    if(props.info.variantId) {
        makeData()
    }else{
        watch(()=> props.info, ()=> {
            makeData()
        }, { deep: true })
    }
    
});
</script>

<style scoped lang="scss">
    .pad {
        padding: 0 12px;
    }
    .variant-info-sty {
        border-radius: 5px;
        background-color: rgba(213, 226, 240, 0.13);
        border: 1px solid rgba(229, 229, 229, 1);
        box-shadow: 0px 1px 2px  rgba(0, 0, 0, 0.25);
    }
    .variant-h {
        font-size: 16px;
        font-weight: bolder;
        padding: 8px 0;
    }
    .variant-message {
        text-align: left;
        .variant-info {
            @extend .variant-info-sty;
            .info-title {
                @extend .pad;
                border-bottom: 1px solid #E5E5E5;
                h3 {
                    @extend .variant-h;
                  text-align: left;
                }
            }
            .info-img {
                padding: 16px 12px;
                span {
                    font-size: 14px;
                    font-weight: bolder;
                    margin-left: 70%;
                }
                img {
                    width: 100%;
                }
            }
            .info-display {
                @extend .pad;
                display: flex;
                div {
                    width: 100%;
                    p {
                        width: 100%;
                        text-align: center;
                    }
                    .li-name {
                        color: #808080;
                    }
                    .li-value {
                        color: #000000;
                        font-weight: bolder;
                    }
                }
            }
            .info-desc {
                margin-top: 40px;
                margin-bottom: 28px;
                @extend .pad;
                .desc-para {
                    p {
                        margin-bottom: 4px;
                        span:first-child {
                            font-weight: bolder;
                        }
                    }
                }
            }
        }
        .variant-transcripts {
            margin-top: 18px;
            @extend .variant-info-sty;
            @extend .pad;
            .transcripts-title {
                h3 {
                    @extend .variant-h;
                  text-align: left;
                }
            }
            .transcripts-type {
                margin-bottom: 20px;
                .type-title {
                    border-bottom: 1px solid rgba(31, 82, 148, 1);
                    padding: 3px 0;
                    span:first-child {
                        color: rgba(31, 82, 148, 1);
                        font-weight: 700;
                        font-size: 14px;
                    }
                    span:last-child {
                        color: rgba(56, 56, 56, 1);
                        font-size: 12px;
                    }
                }
                .type-content {
                    min-height: 100px;
                }
            }
        }
    }
</style>