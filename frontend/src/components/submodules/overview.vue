<template>
    <div class="chat-paper-overview">
        <div class="type-switch">
        </div>
        <div class="type-table" v-for="(item, index) in chatInfo.keyWordSet">
            <CheckBox :info="'gene'" style="margin-top: 0;" v-if="item.name === 'ner_gene'" @switch="switchType"></CheckBox>
            <CheckBox :info="'mutation'" style="margin-top: 0;" v-if="item.name === 'ner_variantId'" @switch="switchType"></CheckBox>
            <CheckBox :info="'disease'" style="margin-top: 0;" v-if="item.name === 'ner_disease'" @switch="switchType"></CheckBox>
            <div class="gene-list">
                <div class="list-item" v-for="i in (status[index] ? item.keyWordSet : item.keyWordSet.slice(0, 6))">
                    <div class="item-name">
                        <n-ellipsis class="ellipsis">{{ i.keyName }}</n-ellipsis>
                    </div>
                    <div class="item-value">{{ i.count }}</div>
                </div>
            </div>
            <div class="gene-more">
                <span @click="moreOrLessData(index)">{{ status[index] ? 'Less' : 'More' }}</span>
            </div>
        </div>
        <div class="acmg-criteria">
            <div class="acmg-title">ACMG Criteria</div>
            <div class="acmg-edit" v-for="(item, index) in chatInfo.criteriaSet">
                <div class="edit-title">
                    <div class="tit-name" :id="item.evidenceCount > 1 ? (item.name + '(' + item.evidenceCount + ')') : item.name">{{ item.evidenceCount > 1 ? (item.name + '(' + item.evidenceCount + ')') : item.name }}</div>
                    <div class="tit-btn" v-show="item.isOpen">
                        <span v-if="item.updateDisabled" @click="editPmForm('edit', index, '')">Edit</span>
                        <span v-else @click="editPmForm('save', index, item.originName)">Save</span>
                    </div>
                </div>
                <div class="edit-tip">
                    <div class="tip-colunm">
                        <span>automatically indentified by <span style="color: #1F5294">ALEPH</span></span>
                    </div>
                    <div class="tip-icon">
                        <n-icon :class="[item.isOpen ? 'expand-triangle' : 'expand-triangle triangle-change']" :component="CaretUpOutline" @click="openCriteria(index)"></n-icon>
                    </div>
                </div>
                <div class="edit-form" v-show="item.isOpen">
                    <n-space vertical>
                        <n-form
                            ref="formRef"
                            :model="item"
                            label-placement="left"
                            :label-width="115"
                            :rules="rules"
                            size="small"
                            require-mark-placement="right-hanging"
                            :disabled="item.updateDisabled"
                        >
                            <div v-if="item.name !== 'PM3' && item.name !=='PP1' && item.name !== 'PS4' && item.name !== 'PS2' && item.name !== 'PM6'">
                                <n-form-item label="Strength" path="strength">
                                    <n-select :options="strengthOptions" v-model:value="item.strength" />
                                </n-form-item>
                            </div>
                            <div v-if="item.name === 'PM3' || item.name === 'BP2'">
                                <div v-if="item.name === 'PM3'">
                                    <n-form-item label="Phase" path="phase">
                                        <n-select :options="pm3PhaseOptions" v-model:value="item.phase" />
                                    </n-form-item>
                                </div>
                                <div v-if="item.name==='BP2'">
                                    <n-form-item label="Phase" path="phase">
                                        <n-select :options="bp2PhaseOptions" v-model:value="item.phase" />
                                    </n-form-item>
                                </div>
                                <n-form-item label="Variant 2" path="allele2">
                                    <n-input v-model:value="item.allele2" placeholder="" />
                                </n-form-item>
                                <n-form-item label="Variant 2 Pathogenicity" path="pathogenicity">
                                    <n-select :options="variant2PathogenicityOptions" v-model:value="item.pathogenicity" />
                                </n-form-item>
                                <n-form-item label="Proband number" path="proband">
                                    <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" v-model:value="item.proband" clearable />
                                </n-form-item>
                            </div>
                            <div v-if="item.name === 'PS4'">
                                <n-form-item label="Phenotype" path="phenotype">
                                    <n-select :options="phenotypeOptions" v-model:value="item.phenotype" />
                                </n-form-item>
                                <n-form-item label="Ancestry" path="ancestry">
                                    <n-select :options="ancestryOptions" v-model:value="item.ancestry" />
                                </n-form-item>
                                <n-form-item label="abc" label-style="font-size: 12px;font-weight: bolder; color: rgba(0,0,0,0)" style="--n-feedback-height: 0px" path="">
                                    <n-input-group>
                                        <div class="ps4-fre">
                                            <div class="fre-child">Mutant allele</div>
                                            <div class="fre-child">Total allele</div>
                                            <div class="fre-child">Allele freq</div>
                                        </div>
                                    </n-input-group>
                                </n-form-item>
                                <n-form-item label="Patient(s)" style="--n-feedback-height: 5px" path="patient_frequency">
                                    <n-input-group>
                                        <div class="ps4-fre">
                                            <div class="fre-child"><n-input-number placeholder="0" style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" v-model:value="item.allele_in_patient" @blur="caculatorPs4Freq(item.originName, item.evidenceCount, 'patient')" clearable /></div>
                                            <div class="fre-child"><n-input-number placeholder="0" style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" v-model:value="item.total_allele_in_patient" @blur="caculatorPs4Freq(item.originName, item.evidenceCount, 'patient')" clearable /></div>
                                            <div class="fre-child"><n-input class="fre-child-input" placeholder="" v-model:value="item.patient_frequency" disabled />%</div>
                                        </div>
                                    </n-input-group>
                                </n-form-item>
                                <n-form-item label="Control(s)" style="--n-feedback-height: 5px" path="control_frequency">
                                    <n-input-group>
                                        <div class="ps4-fre">
                                            <div class="fre-child"><n-input-number placeholder="0" style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" v-model:value="item.allele_in_control" @blur="caculatorPs4Freq(item.originName, item.evidenceCount, 'control')" clearable /></div>
                                            <div class="fre-child"><n-input-number placeholder="0" style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" v-model:value="item.total_allele_in_control" @blur="caculatorPs4Freq(item.originName, item.evidenceCount, 'control')" clearable /></div>
                                            <div class="fre-child"><n-input class="fre-child-input" placeholder="" v-model:value="item.control_frequency" disabled />%</div>
                                        </div>
                                    </n-input-group>
                                </n-form-item>
                                <n-form-item label="text" label-style="font-size: 12px;font-weight: bolder;color: rgba(0,0,0,0)" :show-require-mark="false" style="--n-feedback-height: 20px" path="odd_ratio">
                                    <n-input-group>
                                        <div class="ps4-caculator">
                                            <div class="cac-child">
                                                <div>Odd ratio</div>
                                                <div><n-input class="fre-child-input" placeholder="" v-model:value="item.odd_ratio" disabled /></div>
                                            </div>
                                            <div class="cac-child">
                                                <div>95% confidence interval</div>
                                                <div><n-input class="fre-child-input" style="width: 200px" placeholder="" v-model:value="item.confidence_interval" disabled /></div>
                                            </div>
                                        </div>
                                    </n-input-group>
                                </n-form-item>
                                <n-form-item label="Inheritance" path="inheritance">
                                    <n-select :options="inheritanceOptions" v-model:value="item.inheritance" />
                                </n-form-item>
                                <n-form-item label="Proband number" path="proband">
                                    <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" placeholder="0" v-model:value="item.proband" clearable />
                                </n-form-item>
                            </div>
                            <div v-if="item.name === 'BS3' || item.name === 'PS3'">
                                <n-form-item label="Experiment" path="experiment">
                                    <n-input-group>
                                        <n-input v-model:value="item.experiment"  placeholder="" />
                                    </n-input-group>
                                </n-form-item>
                                <n-form-item label="Function change words" path="function_change">
                                    <n-input-group>
                                        <n-input v-model:value="item.function_change" placeholder="" />
                                    </n-input-group>
                                </n-form-item>
                                <n-form-item label="Figure" style="--n-feedback-height: 10px">
                                    <n-input-group>
                                        <div class="figure-img">

                                        </div>
                                    </n-input-group>
                                </n-form-item>
                            </div>
                            <div v-if="item.name === 'BS4' || item.name === 'PP1' || item.name === 'PP4' || item.name === 'BP5'">
                                <n-form-item :label="item.name !== 'BP5' ? 'Phenotype' : 'Alternate explanation'" path="phenotype">
                                    <n-select :options="phenotypeOptions" v-model:value="item.phenotype" />
                                </n-form-item>
                                <!-- <n-form-item v-if="item.name === 'BP5'" label="Alternate explanation" path="phenotype">
                                    <n-input-group>
                                        <n-input style="font-size: 12px;" placeholder="" v-model:value="item.phenotype" />
                                    </n-input-group>
                                </n-form-item> -->
                                <n-form-item v-if="item.name !== 'BP5'" label="Inheritance" path="inheritance">
                                    <n-select :options="inheritanceOptions" v-model:value="item.inheritance" />
                                </n-form-item>
                                <template v-if="item.name ==='BS4' || item.name ==='PP4' || item.name==='BP5'">
                                    <n-form-item v-if="item.name==='BS4'" label="Genotype" path="genotype">
                                        <n-select :options="genotypeOptions" v-model:value="item.genotype" />
                                    </n-form-item>
                                    <n-form-item v-if="item.name ==='BS4'" label="Case number" path="case">
                                        <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" placeholder="0" v-model:value="item.case" clearable />
                                    </n-form-item>
                                    <n-form-item v-if="item.name !=='BS4'" label="Proband number" path="proband">
                                        <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" placeholder="0" v-model:value="item.proband" clearable />
                                    </n-form-item>
                                </template>
                                <template v-if="item.name === 'PP1'">
                                    <n-form-item label="test" label-style="font-size: 12px;font-weight: bolder;color: rgba(0,0,0,0)" style="--n-feedback-height: 10px" path="">
                                        <n-input-group>
                                            <div class="segregation-chart">
                                                <div class="sc-content">
                                                    <div class="sc-left">
                                                        <span>Pedigree Chart</span>
                                                        <div class="pedigree-chart">
                                                            <img src="" alt="">
                                                        </div>
                                                    </div>
                                                    <div class="sc-right">
                                                        <p>There is <span class="sc-span" style="color: #D43030">{{ item.affected_segregation }}</span> Affect and <span class="sc-span" style="color: #2A82E4">{{ item.unaffected_segregation }}</span> Unaffect segregatons.</p>
                                                        <p>LOD score is <span class="sc-span" style="color: #2A82E4">{{ item.lod_score }}</span></p>
                                                        <p><n-button size="tiny" type="info">Upload</n-button> a new chart</p>
                                                    </div>
                                                </div>
                                                <div class="aul-ses">
                                                    <div class="ses-input">
                                                        <div>Affected Segregation</div>
                                                        <n-input-number style="--n-font-size: 12px; font-size: 12px; width: 130px;" size="small" min="0" placeholder="0" v-model:value="item.affected_segregation" @update:value="caculatorLodScore(item.originName, item.evidenceCount)" clearable />
                                                    </div>
                                                    <div class="ses-input" v-if="item.inheritance === 'AR'">
                                                        <div>Unaffected Segregation</div>
                                                        <n-input-number style="--n-font-size: 12px; font-size: 12px; width: 130px;" size="small" min="0" placeholder="0" v-model:value="item.unaffected_segregation" @update:value="caculatorLodScore(item.originName, item.evidenceCount)" clearable />
                                                    </div>
                                                    <div class="ses-input">
                                                        <div>LOD score</div>
                                                        <n-input style="--n-font-size: 12px; font-size: 12px; width: 130px;" size="small" placeholder="" v-model:value="item.lod_score" disabled />
                                                    </div>
                                                </div>
                                            </div>
                                        </n-input-group>
                                    </n-form-item>
                                </template>
                            </div>
                            <div v-if="item.name === 'PS2' || item.name === 'PM6'">
                                <n-form-item label="Status" path="denovo_status">
                                    <n-select :options="statusOptions" v-model:value="item.denovo_status" />
                                </n-form-item>
                                <n-form-item label="Phenotype" path="phenotype">
                                    <n-select :options="phenotypeOptions" v-model:value="item.phenotype" />
                                </n-form-item>
                                <n-form-item label="Consistency" path="phenotypic_consistency">
                                    <n-select :options="consistencyOptions" v-model:value="item.phenotypic_consistency" />
                                </n-form-item>
                                <n-form-item label="Proband number" path="proband">
                                    <n-input-group>
                                        <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" placeholder="0" v-model:value="item.proband" clearable />
                                    </n-input-group>
                                </n-form-item>
                            </div>
                            <n-form-item label="Evidence" path="evidenceValue">
                                <n-input
                                    v-model:value="item.evidence"
                                    placeholder=""
                                    type="textarea"
                                    :autosize="{
                                        minRows: 3,
                                        maxRows: 5
                                    }"
                                />
                            </n-form-item>
                        </n-form>
                    </n-space>
                </div>
            </div>
            <!-- <div class="acmg-edit">
                <div class="edit-title">
                    <div class="tit-name">PM1</div>
                    <div class="tit-btn">Edit</div>
                </div>
                <div class="edit-tip">
                    <span>automatically indentified by <span style="color: #1F5294">AutoLOD</span></span>
                </div>
                <div class="edit-pic">
                    <img src="https://img.js.design/assets/img/6433e046d4a8cd609b2aa152.png#35c9fe8b7a086ba07e12e5298dcc515d" alt="">
                </div>
            </div> -->
        </div>
    </div>
</template>

<script setup lang='ts'>
    import { ref, watch, onMounted } from 'vue';
    import { FormInst } from 'naive-ui';
    import { CaretUpOutline } from '@vicons/ionicons5';
    import CheckBox from "./checkbox.vue";
    import { strengthOptions, pm3PhaseOptions, bp2PhaseOptions, variant2PathogenicityOptions, phenotypeOptions,
             inheritanceOptions, genotypeOptions, statusOptions, consistencyOptions, ancestryOptions } from '@/static/criteria';
    import { useChatInfo } from '@/store';
    import { Message } from "@/utils/utils";
    import { paperCriteriaForm } from '@/static/newForm'
    import { useRoute } from "vue-router";
    import { getPaperCriteriaShowing, updatePaperCriteria } from '@/api/request'

    const route = useRoute();
    const pmid = route.query.pmid;
    const chatInfo: any = useChatInfo();
    const status = ref<boolean[]>([false, false, false]);
    const formRef = ref<FormInst | null>(null);
    const currentCriteriaIndex = ref<number>(0);

    let d:any = null;
    const rules = {
        strength: {
            key: 'strength',
            required: true,
            trigger: ['change', 'blur'],
            message: 'please select strength'
        },
        phase: {
            key: 'phase',
            required: true,
            trigger: ['change', 'blur'],
            message: 'please select phase'
        },
        allele2: {
            key: 'allele2',
            required: true,
            trigger: ['blur', 'input'],
            message: 'please input Variant 2'
        },
        pathogenicity: {
            key: 'pathogenicity',
            required: true,
            trigger: ['change', 'blur'],
            message: 'please input Variant 2 Pathogenicity'
        },
        proband: {
            key: 'proband',
            type: 'number',
            required: true,
            message: 'Please input Proband number',
            trigger: 'blur'
        },
        case: {
            key: 'case',
            type: 'number',
            required: true,
            message: 'Please input Proband number',
            trigger: 'blur'
        },
        experiment: {
            key: 'experiment',
            required: true,
            trigger: ['blur', 'input'],
            message: 'please input experiment'
        },
        function_change: {
            key: 'function_change',
            required: true,
            trigger: ['blur', 'input'],
            message: 'please input function_change'
        },
        phenotype: {
            key: 'phenotype',
            required: true,
            trigger: ['change', 'blur'],
            message: 'please select phenotype'
        },
        inheritance: {
            key: 'inheritance',
            required: true,
            trigger: ['change', 'blur'],
            message: 'please select inheritance'
        },
        ancestry: {
            key: 'ancestry',
            required: true,
            message: 'Please select ancestry',
            trigger: ['change', 'blur'],
        },
        genotype: {
            key: 'genotype',
            required: true,
            message: 'Please select genotype',
            trigger: ['change', 'blur'],
        },
        denovo_status: {
            key: 'denovo_status',
            required: true,
            message: 'Please select denovo_status',
            trigger: ['change', 'blur'],
        },
        phenotypic_consistency: {
            key: 'phenotypic_consistency',
            required: true,
            message: 'Please select Consistency',
            trigger: ['change', 'blur'],
        },
        patient_frequency: {
            key: 'patient_frequency',
            // type: 'number',
            required: true,
            message: '',
            trigger: 'blur'
        },
        control_frequency: {
            key: 'control_frequency',
            // type: 'number',
            required: true,
            message: '',
            trigger: 'blur'
        },
        odd_ratio: {
            key: 'odd_ratio',
            required: true,
            message: 'Please input Patient(s) and Control(s)',
            trigger: 'change'
        }
    }

    const moreOrLessData = (index: number) => {
        status.value[index] = !status.value[index];
    }
    const switchType = (checkStatus: any) => {
        console.log(checkStatus)
        chatInfo.getSwitchStatus(checkStatus);
    }
    const openCriteria = (index: number) => {
        let bool = chatInfo.criteriaSet[index].isOpen;
        chatInfo.criteriaSet[index].isOpen = !bool;
    }
    const editPmForm = (status: string, index: number, originName: string) => {
        if(status === 'edit') {
            chatInfo.criteriaSet[index].updateDisabled = false;
        }else{
            currentCriteriaIndex.value = index;
            let obj: any = {};
            for(let i in paperCriteriaForm[originName]) {
                obj[i] = chatInfo.criteriaSet[index][i];
            }
            if(formRef.value) {
                formRef.value[index].validate((errors: any) => {
                    if (!errors) {
                        chatInfo.loading = true;
                        let info = {
                            info: {
                                acmgs: {
                                    [originName]: {
                                        // strength: chatInfo.criteriaSet[index].baseInfo.strength,
                                        // description: chatInfo.criteriaSet[index].baseInfo.description,
                                        evidences: obj
                                    }
                                }
                            }
                        }
                        let param = Object.assign(paperCriteriaForm.baseInfor, info);
                        console.log(param)
                        updatePaperCriteria(param).then(res=> {
                            console.log(res);
                            if(res.data.code === 1000) {
                                if(!obj.id) {
                                    chatInfo.criteriaSet[index].id = res.data.data;
                                }
                                Message.success('Save success');
                                chatInfo.criteriaSet[index].updateDisabled = true;
                            }else{
                                Message.error('Save fail');
                            }
                            chatInfo.loading = false;
                        })
                    } else {
                        Message.warning('Please fill in the required information')
                    }
                })
            }
        }
    }
    const getCriteriaData = () => {
        if(pmid !== '21738395' && pmid !== '16460646') return
        chatInfo.updateCriteriaSet([]);
        getPaperCriteriaShowing({pmid: pmid, variantId: '7-107315505-T-A', userId: '22'}).then(res=> {
            console.log(res)
            if(res.data.code === 3002) { Message.error(res.data.msg); return }
            const { data } = res.data;
            let dataSet:any = [];
            Object.keys(data.acmgs).forEach((v:any)=> {
                data.acmgs[v].evidences.forEach((t: any, index: number)=> {
                    let obj = {};
                    for(let i in paperCriteriaForm[v]) {
                        if(i === 'affected_segregation' || i === 'unaffected_segregation') {
                            obj[i] = Number(t[i]);
                        }else if(i === 'proband' || i === 'case') {
                            if (typeof t[i] === 'object' && t[i] !== null && !Array.isArray(t[i])) {
                                obj[i] = Number(t[i].low);
                            }
                            else if(typeof t[i] === 'string') {
                                obj[i] = Number(t[i]);
                            }
                            else{
                                obj[i] = t[i];
                            }
                        }else if(i === 'patient_frequency' || i === 'control_frequency') {
                            if(t[i] === '' || t[i] === null) obj[i] = '';
                            if(Number(t[i]) >= 0) {
                                obj[i] = String((Number(t[i]) * 100).toFixed(4));
                            }
                        }else if(i === 'lod_score') {
                            obj[i] = String(t[i]);
                        }
                        else{
                            obj[i] = t[i];
                        }
                    }
                    dataSet.push(Object.assign({ name: v.toUpperCase(), originName: v, isOpen: false, updateDisabled: true, evidenceCount: index + 1, baseInfo: {description: data.acmgs[v].description, strength: data.acmgs[v].strength }}, obj))
                })
            })
            dataSet[0].isOpen = true;
            chatInfo.updateCriteriaSet(dataSet);
        })
    }
    const caculatorLodScore = (originName: string, evidenceCount: number)=> {   
        let findIndex = chatInfo.criteriaSet.findIndex((v: any)=> {
            return v.originName === originName && v.evidenceCount === evidenceCount; 
        }) 
        let affectedVal = chatInfo.criteriaSet[findIndex].affected_segregation;
        let unAffectedVal = chatInfo.criteriaSet[findIndex].unaffected_segregation;
        if(d !== null) {
            d.destroy();
        }
        if(chatInfo.criteriaSet[findIndex].inheritance === 'AR') {
            if(affectedVal === null && unAffectedVal === null) {
                if(!d) d = Message.warning('Please enter the correct value');
                return
            }
            if(affectedVal === null || unAffectedVal === null) {
                chatInfo.criteriaSet[findIndex].lod_score = '';
            }
            if(affectedVal !== null && unAffectedVal !== null) {
                chatInfo.criteriaSet[findIndex].lod_score = Math.log10(1 / ((Math.pow(0.25, affectedVal)*(Math.pow(0.75, unAffectedVal))))).toFixed(4);
            }
        }else{
            if(affectedVal === null) {
                chatInfo.criteriaSet[findIndex].lod_score = '';
                if(!d) d = Message.warning('Please enter the correct value');
                return 
            }else{
                chatInfo.criteriaSet[findIndex].lod_score = Math.log10(1 / Math.pow(0.5, affectedVal)).toFixed(4);
            }
        }
        d = null;
    }
    const caculatorPs4Freq = (originName: string, evidenceCount: number, type: string)=> {
        let findIndex = chatInfo.criteriaSet.findIndex((v: any)=> {
            return v.originName === originName && v.evidenceCount === evidenceCount; 
        })
        let { allele_in_patient, total_allele_in_patient, allele_in_control, total_allele_in_control } = chatInfo.criteriaSet[findIndex];
        const isNullP = allele_in_patient === '' || allele_in_patient === null;
        const isNullC = allele_in_control === '' || allele_in_control === null;
        const isNullTotalP = total_allele_in_patient === '' || total_allele_in_patient === null;
        const isNullTotalC = total_allele_in_control === '' || total_allele_in_control === null;
        const a = Number(allele_in_patient);
        const b = Number(total_allele_in_patient)  - Number(allele_in_patient);
        const c = Number(allele_in_control);
        const d = Number(total_allele_in_control) - Number(allele_in_control);
        if(type === 'patient') {
            if(isNullP || isNullTotalP) {
                chatInfo.criteriaSet[findIndex].patient_frequency = '';
                chatInfo.criteriaSet[findIndex].odd_ratio = '';
                chatInfo.criteriaSet[findIndex].confidence_interval = '';
                return
            }
            if(Number(total_allele_in_patient) < a) {
                chatInfo.criteriaSet[findIndex].total_allele_in_patient = null;
                chatInfo.criteriaSet[findIndex].patient_frequency = '';
                chatInfo.criteriaSet[findIndex].odd_ratio = '';
                chatInfo.criteriaSet[findIndex].confidence_interval = '';
                Message.error('Total allele Cannot be less than Mutant allele');
            }else if(Number(total_allele_in_patient) === 0) {
                chatInfo.criteriaSet[findIndex].total_allele_in_patient = null;
                chatInfo.criteriaSet[findIndex].patient_frequency = '';
                chatInfo.criteriaSet[findIndex].odd_ratio = '';
                chatInfo.criteriaSet[findIndex].confidence_interval = '';
                Message.error('Patients Total allele Cannot be 0');
            }else{
                chatInfo.criteriaSet[findIndex].patient_frequency= String(((a / Number(total_allele_in_patient))*100).toFixed(4));
            } 
        }
        if(type === 'control') {
            if(isNullC || isNullTotalC) {
                chatInfo.criteriaSet[findIndex].control_frequency = '';
                chatInfo.criteriaSet[findIndex].odd_ratio = '';
                chatInfo.criteriaSet[findIndex].confidence_interval = '';
                return
            }
            if(Number(total_allele_in_control) < c) {
                chatInfo.criteriaSet[findIndex].total_allele_in_control = null;
                chatInfo.criteriaSet[findIndex].control_frequency = '';
                chatInfo.criteriaSet[findIndex].odd_ratio = '';
                chatInfo.criteriaSet[findIndex].confidence_interval = '';
                Message.error('Total allele Cannot be less than Mutant allele');
            }else if(Number(total_allele_in_control) === 0) {
                chatInfo.criteriaSet[findIndex].total_allele_in_control = null;
                chatInfo.criteriaSet[findIndex].control_frequency = '';
                chatInfo.criteriaSet[findIndex].odd_ratio = '';
                chatInfo.criteriaSet[findIndex].confidence_interval = '';
                Message.error('Controls Total allele Cannot be 0');
            }else{
                chatInfo.criteriaSet[findIndex].control_frequency = String(((c / Number(total_allele_in_control))*100).toFixed(4));
            }
        }
        if(isNullP || isNullC || isNullTotalP || isNullTotalC || Number(total_allele_in_patient) === 0 || Number(total_allele_in_control) === 0) {
            chatInfo.criteriaSet[findIndex].odd_ratio = '';
            chatInfo.criteriaSet[findIndex].confidence_interval = '';
            return
        }
        if(c === 0) {
            chatInfo.criteriaSet[findIndex].odd_ratio = 'NA';
            chatInfo.criteriaSet[findIndex].confidence_interval = 'NA';
            return
        }
        chatInfo.criteriaSet[findIndex].odd_ratio = String(((a / c) / (b / d)).toFixed(4));
        const r = Number(chatInfo.criteriaSet[findIndex].odd_ratio);
        const cinit = Math.sqrt((1 / a) + (1 / b) + (1 / c) + (1 / d));
        let y = Math.log(r) - (1.96 * cinit);
        let z = Math.log(r) + (1.96 * cinit);
        y = Math.exp(y);
		z = Math.exp(z);
		y = Math.round(y * 10000) / 10000;
		z = Math.round(z * 10000) / 10000;
        chatInfo.criteriaSet[findIndex].confidence_interval = 'from ' + y + ' to ' + z;
        if(formRef.value) {
            formRef.value[currentCriteriaIndex.value].validate((errors: any) => {
                if (!errors) {}
            },(rule: any) => {
                return rule?.key !== 'odd_radio' && rule?.key !== 'patient_frequency' && rule?.key !== 'control_frequency'
            })
        }
    }
    watch(()=> chatInfo.criteriaRelate, ()=> {
        console.log(chatInfo.criteriaRelate);
    }, {deep: true})

    onMounted(()=> {
        getCriteriaData();
    })
</script>

<style lang='scss'>
    .chat-paper-overview {
        .type-switch {
            display: flex;
            text-align: left;
            align-items: center;
        }
        .type-table {
            text-align: left;
            // margin-bottom: 6px;
            .gene-list {
                width: 100%;
                border-top: 1px solid #A6A6A6;
                border-bottom: 1px solid #A6A6A6;
                display: flex;
                flex-wrap: wrap;
                margin-top: 5px;
                .list-item {
                    display: flex;
                    width: 50%;
                    .item-name {
                        width: 75%;
                        text-align: left;
                        position: relative;
                        .ellipsis {
                            width: 100%;
                            position: absolute;
                            left: 0;
                        }
                    }
                    .item-value {
                        width: 25%;
                        text-align: center;
                    }
                }
            }
        }
        .gene-more {
            text-align: right;
            font-size: 14px;
            color: #638BB8;
            cursor: pointer;
            margin-top: 2px;
        }
        .acmg-criteria {
            margin-top: 16px;
            text-align: left;
            .acmg-title {
                font-size: 14px;
                font-weight: bold;
                color: #1F5294;
            }
            .acmg-edit {
                .edit-title {
                    display: flex;
                    align-items: center;
                    .tit-name {
                        width: 50%;
                        font-size: 16px;
                        font-weight: bolder;
                        color: #FF5733;
                    }
                    .tit-btn {
                        width: 50%;
                        font-size: 14px;
                        font-weight: bold;
                        color: #0E488A;
                        text-align: right;
                        cursor: pointer;
                    }
                }
                .edit-tip {
                    border-bottom: 1px solid #E5E5E5;
                    margin-bottom: 10px;
                    display: flex;
                    align-items: center;
                    .tip-colunm {
                        width: 100%;
                        span {
                            color: #808080;
                        }
                    }
                    .tip-icon {
                        width: 40px;
                        text-align: right;
                        .expand-triangle {
                            font-size: 20px;
                            &:hover {
                                color: #000;
                            }
                        }
                        .triangle-change {
                            transform: rotateX(180deg);
                        }
                    }
                }
                .edit-form {
                    .ps4-fre {
                        display: flex;
                        .fre-child {
                            margin-right: 10px;
                            width: 100px;
                            color: #383838;
                        }
                        .fre-child:last-child {
                            width: 115px;
                        }
                    }
                    .ps4-caculator {
                        // display: flex;
                        .cac-child {
                            width: 200px;
                            // margin-bottom: 10px;
                            color: #383838;
                        }
                    }
                    .fre-child-input {
                        width: 74px;
                        background-color: #90BBEB;
                        input {
                            color: #000 !important;
                        }
                    }
                    .figure-img {
                        width: 200px;
                        height: 80px;
                        border: 1px solid #1F5294;
                        border-radius: 8px;
                    }
                    .segregation-chart {
                        width: 100%;
                        border: 1px solid rgba(42, 130, 228, 0.27);
                        border-radius: 5px;
                        padding: 5px 10px;
                        color: #000000;
                        .sc-content {
                            display: flex;
                            align-items: center;
                            .sc-left {
                                width: 50%;
                                margin-right: 10px;
                                text-align: center;
                                .pedigree-chart {
                                    width: 100%;
                                    height: 160px;
                                    background-color: gold;
                                    border-radius: 5px;
                                }
                            }
                            .sc-right {
                                width: 50%;
                                p {
                                    line-height: 1.7;
                                    .sc-span {
                                        font-weight: bolder;
                                        font-size: 16px;
                                    }
                                }
                            }
                        }
                        .aul-ses {
                            margin: 10px 0;
                            // display: flex;
                            // align-items: center;
                            .ses-input {
                                // width: 30%;
                                margin-bottom: 8px;
                            }
                        }
                    }
                }
                .edit-pic {
                    height: 540px;
                    img {
                        width: 100%;
                        height: 100%;
                    }
                }
            }
        }
    }
</style>