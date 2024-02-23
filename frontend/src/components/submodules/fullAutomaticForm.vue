<template>
    <div class="full-auto">
        <n-spin :show="loading" :rotate="false">
            <template #icon>
                <n-icon>
                    <BounceLoader
                        :loading="loading"
                        color="#0954AB"
                        size="60px" />
                </n-icon>
            </template>
            <n-scrollbar style="height: 100%; background-color: #F1F1F1; border-radius: 5px;">
                <div class="auto-form">
                    <n-form ref="formRef" size="small" label-placement="left" label-width="160" require-mark-placement="right-hanging" :model="fullAutoFormData" :rules="rules">
                        <div>
                            <template v-if="type === 'PVS1'">
                                <div class="pvs1-flow">
                                    <div class="flow-title">
                                        <span>PVS1 flowchat</span><span> interpreted by </span><span>AutoPVS1</span>
                                    </div>
                                    <div class="flow-chart">
                                        <template v-for="item in pvs1FlowChat">
                                            <button>{{item}}</button>
                                            <span>↓</span>
                                        </template>
                                        <button class="chart-btn">PVS1</button>
                                    </div>
                                </div>
                            </template>
                            <template v-if="type === 'BS1' || type === 'BA1' || type === 'PM2' || type === 'PVS1' || type === 'PP3' || type === 'BP4' || type === 'BP3' || type === 'BP7' || type === 'PM4' || type === 'PM5' || type === 'PS1' || type === 'PM1'">
                                <n-form-item label="Strength" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="strength">
                                    <n-radio-group style="width: 94%;" v-model:value="fullAutoFormData.strength">
                                        <n-radio-button class="radio-btn radio-btn-left" label='Supporting' value='Supporting' :style="fullAutoFormData.strength === 'Supporting' ? 'background-color: #FFC300; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn" label='Moderate' value='Moderate' :style="fullAutoFormData.strength === 'Moderate' ? 'background-color: #FF8D1A; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn" label='Strong' value='Strong' :style="fullAutoFormData.strength === 'Strong' ? 'background-color: #12990F; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn" label='VeryStrong' value='VeryStrong' :style="fullAutoFormData.strength === 'VeryStrong' ? 'background-color: #D43030; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn radio-btn-right" label='Unmet' value='Unmet' :style="fullAutoFormData.strength === 'Unmet' ? 'background-color: #383838; color: #ffffff;' : ''"></n-radio-button>
                                    </n-radio-group>
                                </n-form-item>
                            </template>
                            <template v-if="type === 'BS2'">
                                <n-form-item label="Strength" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="strength">
                                    <n-radio-group style="width: 94%;" v-model:value="fullAutoFormData.strength">
                                        <n-radio-button class="radio-btn radio-btn-left" label='Unmet' value='Unmet' :style="fullAutoFormData.strength === 'Unmet' ? 'background-color: #383838; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn radio-btn-right" label='Strong' value='Strong' :style="fullAutoFormData.strength === 'Strong' ? 'background-color: #12990F; color: #ffffff;' : ''"></n-radio-button>
                                    </n-radio-group>
                                </n-form-item>
                            </template>
                            <template v-if="type === 'BS1' || type === 'BA1' || type === 'PM2' || type === 'BS2'">
                                <n-form-item label="Inheritance" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="inheritance">
                                    <n-radio-group style="width: 94%;" v-model:value="fullAutoFormData.inheritance">
                                        <n-radio-button class="radio-btn radio-btn-left" label='AD' value='AD' :style="fullAutoFormData.inheritance === 'AD' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn" label='AR' value='AR' :style="fullAutoFormData.inheritance === 'AR' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn" label='X-Link' value='X-Link' :style="fullAutoFormData.inheritance === 'X-Link' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn radio-btn-right" label='Y-Link' value='Y-Link' :style="fullAutoFormData.inheritance === 'Y-Link' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    </n-radio-group>
                                </n-form-item>
                            </template>
                            <template v-if="type === 'BS1' || type === 'BA1' || type === 'PM2' || type === 'BS2'">
                                <n-form-item :label="type==='BS2' ? 'Homozygotes individuals' : 'Popmax Filtering AF'" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                    <span class="popmax-freq">{{ type === 'BS2' ? fullAutoFormData.homozygote : fullAutoFormData.af ? (fullAutoFormData.af * 100 + '%') : 0 }}</span>
                                </n-form-item>
                                <n-form-item v-if="type !== 'BS2'" label="Related Criteria" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                    <RelateProgress :info="props.info" :inheritance="fullAutoFormData.inheritance" />
                                </n-form-item>
                                <n-form-item v-if="type === 'BS2'" label="Related Criteria" label-style="font-size: 12px;font-weight: bolder;color: rgba(0,0,0,0)" style="--n-feedback-height: 10px">
                                    <RelateProgress :info="props.info" :inheritance="fullAutoFormData.inheritance" />
                                </n-form-item>
                            </template>
                            <template v-if="type === 'PP3' || type === 'BP4' || type === 'BP3' || type === 'BP7' || type === 'PM4' || type === 'PM5' || type === 'PS1' || type === 'PM1'">
                                <n-form-item label="Mutation Type" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                    <n-input-group style="width: 94%; flex-wrap: wrap;">
                                        <n-button class="btn-group" type="default" :disabled="i.selected ? false : true" size="small" v-for="i in btnGroup" :key="i.key" :style="i.selected ? 'background-color: #2A82E4; color: #ffffff' : ''" @click="selectMutationType(i.name)">{{ i.name }}</n-button>
                                    </n-input-group>
                                </n-form-item>
                                <template v-if="type === 'PP3' || type === 'BP4'">
                                    <n-form-item label="Revel score" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                        <span class="popmax-freq" style="color: #942222;">{{ fullAutoFormData.revelScore ?? 0 }}</span>
                                    </n-form-item>
                                    <n-form-item label="Related critera" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                        <RelateProgress :info="props.info" />
                                    </n-form-item>
                                    <n-form-item label="Impact splicing" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                        <n-switch :rail-style="SwitchStyle" size="small" v-model:value="fullAutoFormData.impactSplicing" disabled>
                                            <template #unchecked>
                                                <span>No</span>
                                            </template>
                                            <template #checked>
                                                <span>Yes</span>
                                            </template>
                                        </n-switch>
                                    </n-form-item>
                                </template>
                                <template v-if="type === 'PM4' || type === 'BP3'">
                                    <n-form-item label="In non-repeated region" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                        <n-switch :rail-style="SwitchStyle" size="small" v-model:value="fullAutoFormData.repeatedRegion" disabled>
                                            <template #unchecked>
                                                <span>No</span>
                                            </template>
                                            <template #checked>
                                                <span>Yes</span>
                                            </template>
                                        </n-switch>
                                    </n-form-item>
                                </template>
                                <template v-if="type === 'BP7'">
                                    <n-form-item label="GERP score" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                        <span class="popmax-freq" style="color: #2A82E4;">{{ fullAutoFormData.gerpScore ?? 0 }}</span>
                                    </n-form-item>
                                    <n-form-item label="Impact splicing" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                        <n-switch :rail-style="SwitchStyle" size="small" v-model:value="fullAutoFormData.impactSplicing" disabled>
                                            <template #unchecked>
                                                <span>No</span>
                                            </template>
                                            <template #checked>
                                                <span>Yes</span>
                                            </template>
                                        </n-switch>
                                    </n-form-item>
                                </template>
                                <template v-if="type === 'PM1'">
                                    <n-form-item label="Hotspot region" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 5px">
                                        <span style="text-decoration: underline; color: #808080;">{{ fullAutoFormData.hotRegion ?? 'NA' }}</span>
                                    </n-form-item>
                                    <n-form-item label="Domain region" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 5px">
                                        <span style="text-decoration: underline; color: #808080;">{{ fullAutoFormData.domainRegion ?? 'NA' }}</span>
                                    </n-form-item>
                                    <n-form-item label="Curated region" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 5px">
                                        <span style="text-decoration: underline; color: #808080;">{{ fullAutoFormData.curatedRegion ?? 'NA' }}</span>
                                    </n-form-item>
                                </template>
                                <template v-if="type === 'PM5' || type === 'PS1'">
                                    <n-form-item label="Related variants" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 15px">
                                        <n-input-group style="width: 94%;">
                                            <div class="pm1-related">
                                                <div class="related-common related-header">
                                                    <div>Variants</div>
                                                    <div>Pathogenicity</div>
                                                    <div>Source</div>
                                                </div>
                                                <div class="related-common">
                                                    <div style="color: #235EA1;">c.148G>T(p.Asp50Tyr)</div>
                                                    <div style="color: rgb(212, 48, 48);">Pathogenic</div>
                                                    <div style="color: #808080;">ClinVar</div>
                                                </div>
                                            </div>
                                        </n-input-group>
                                    </n-form-item>
                                </template>
                            </template>
                            <n-form-item label="Other Comment" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="comment">
                                <n-input-group style="width: 94%;">
                                    <!-- <n-input style="font-size: 12px;" placeholder="Input comment" v-model:value="fullAutoFormData.comment" /> -->
                                    <n-input placeholder="Input comment" v-model:value="fullAutoFormData.comment" type="textarea" style="font-size: 12px;" :autosize="{ minRows: 3, maxRows: 5 }"/>
                                </n-input-group>
                            </n-form-item>
                        </div>
                    </n-form>
                    <div class="auto-btn">
                        <div class="hidden-ele"></div>
                        <div class="show-btn">
                            <n-button class="btn-sty" type="success" size="small" @click="saveFullAutomatic">Save</n-button>
                        </div>
                    </div>
                </div>
            </n-scrollbar>
        </n-spin>
    </div>
</template>

<script setup lang='ts'>
    import { ref, reactive, onMounted } from 'vue';
    import { FormInst } from 'naive-ui'
    import { BounceLoader } from "vue3-spinner";
    import { Message } from "@/utils/utils";
    import { SwitchStyle } from "@/utils/style"
    import RelateProgress from './relatedProgress.vue';
    import { updateEvidence } from "@/api/request"

    const props = defineProps(['info']);
    const formRef = ref<FormInst | null>(null);
    console.log(props.info)
    const type = ref<string>(props.info.rowData.rule);
    const pvs1FlowChat = ref<[]>([]);
    const loading = ref<boolean>(false);
    const emit = defineEmits(['update']);

    const btnGroup = reactive([{
        key: 1,
        name: 'nonsense',
        selected: false
    },{
        key: 2,
        name: 'frameshift',
        selected: false
    },{
        key: 3,
        name: 'Splicing_variant',
        selected: false
    },{
        key: 4,
        name: 'missense',
        selected: false
    },{
        key: 5,
        name: 'terminator_codon_variant',
        selected: false
    },{
        key: 6,
        name: 'initiator_codon_variant',
        selected: false
    },{
        key: 7,
        name: 'inframe',
        selected: false
    },{
        key: 8,
        name: 'UTR_variant',
        selected: false
    },{
        key: 9,
        name: 'intron',
        selected: false
    },{
        key: 10,
        name: 'synonymous',
        selected: false
    }])

    let fullAutoFormData = reactive<any>({
        id: '',
        userId: '21',
        name: '',
        variantId: '',
        strength: '',
        inheritance: '',
        consequence: '',
        af: '',
        revelScore: '',
        homozygote: '',
        evidence: '',
        hotRegion: '',
        domainRegion: '',
        curatedRegion: '', 
        repeatedRegion: false,
        gerpScore: '',
        impactSplicing: false,
        comment: ''
    })

    const getFullAutoDatas = () => {
        for(let i in fullAutoFormData) {
            if(i === 'impactSplicing' || i === 'repeatedRegion') {
                fullAutoFormData[i] = (props.info.rowData[i] === null || props.info.rowData[i] === false) ? false : true;
            }else{
                fullAutoFormData[i] = props.info.rowData[i];
            }
        }
        fullAutoFormData['userId'] = 21;
        fullAutoFormData['name'] = props.info.rowData['rule'];
        fullAutoFormData['variantId'] = props.info.variantId;
        if(props.info.rowData['rule'] === 'PVS1') {
            pvs1FlowChat.value = fullAutoFormData.evidence.split('->');
        }
        if(fullAutoFormData['consequence']) {
            selectMutationType(fullAutoFormData['consequence']);
        }
    }
    const saveFullAutomatic = () => {
        console.log(fullAutoFormData)
        formRef.value?.validate((errors) => {
            if (!errors) {
                loading.value = true;
                updateEvidence(JSON.parse(JSON.stringify(fullAutoFormData))).then(res=> {
                    if(res.data.code === 1000) {
                        Message.success('Update success');
                        emit('update', fullAutoFormData);
                        loading.value = false;
                    }
                })
            }else{
                Message.warning('Please fill in the required information')
            }
        })
    }

    let rules = {
        strength: {
            required: true,
            message: 'Please select strength',
            trigger: 'change'
        },
        inheritance: {
            required: true,
            message: 'Please select inheritance',
            trigger: 'change'
        }
    }
    const selectMutationType = (name: string) => {
        for(let i in btnGroup) {
            btnGroup[i].selected = false;
        }
        let findIndex = btnGroup.findIndex(v=> v.name === name);
        if(findIndex > -1) {    
            btnGroup[findIndex].selected = true;
        }
    }
    onMounted(()=> {
        getFullAutoDatas();
    })

</script>

<style lang='scss'>
    .full-auto {
        height: calc(100% - 10px);
        border: 1px solid #F1F1F1;
        border-radius: 8px;
        padding: 4px;
        background-color: #ffffff;
        .auto-form {
            padding: 10px 6px;
            border-radius: 8px;
            font-size: 12px;
            .radio-btn {
                font-size: 12px;
                width: 100px;
                text-align: center;
            }
            .radio-btn-left {
                border-top-left-radius: 20px !important;
                border-bottom-left-radius: 20px !important;
                .n-radio-button__state-border {
                    border-top-left-radius: 20px !important;
                    border-bottom-left-radius: 20px !important;
                }
            }
            .radio-btn-right {
                border-top-right-radius: 20px !important;
                border-bottom-right-radius: 20px !important;
                .n-radio-button__state-border {
                    border-top-right-radius: 20px !important;
                    border-bottom-right-radius: 20px !important;
                }
            }
            .pvs1-flow {
                text-align: center;
                .flow-title {
                    span {
                        font-size: 14px;
                    }
                    span:first-child {
                        font-weight: bolder;
                        color: #000000;
                    }
                    span:nth-child(2) {
                        font-size: 12px;
                        color: #808080;
                        margin: 0 6px;
                    }
                    span:last-child {
                        font-weight: bolder;
                        color: #2A82E4;
                    }
                }
                .flow-chart {
                    margin: 10px 0 20px 0;
                    button {
                        display: block;
                        border: 1px solid #383838;
                        border-radius: 4px;
                        padding: 6px 10px;
                        margin: 0 auto;
                        font-size: 12px;
                    }
                    span {
                        color: #383838;
                    }
                    .chart-btn {
                        border: none;
                        background-color: #942222;
                        color: #ffffff;
                    }
                }
            }
            .inheri-btn {
                width: 100px;
                font-size: 12px;
            }
            .popmax-freq {
                font-size: 20px;
                font-weight: bolder;
                color: #2A82E4;
            }
            .btn-group {
                margin-right: 4px;
                margin-bottom: 6px;
                font-size: 12px;
            }
            .pm1-related {
                width: 100%;
                padding: 6px 4px;
                border-radius: 5px;
                border: 1px solid rgba(229, 229, 229, 1);
                box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
                .related-common {
                    display: flex;
                    align-items: center;
                    div {
                        width: 33.33%;
                        padding: 3px 0;
                    }
                }
                .related-header {
                    color: #808080;
                    border-bottom: 1px solid #E5E5E5;
                }
            }
            .auto-btn {
                display: flex;
                .hidden-ele {
                    width: 160px;
                }
                .show-btn {
                    width: calc(94% - 151px);
                    text-align: right;
                    .btn-sty {
                        width: 80px;
                        font-size: 12px;
                        background-color: #7795BB;
                    }
                }
            }
        }
    }
</style>