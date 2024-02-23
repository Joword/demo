<template>
    <div class="criteria-dialog">
        <div class="pmid-list" v-show="!isFullAutomatic">
            <div class="label-sty">
                <n-icon style="margin-bottom: 6px;"><ReaderOutline class="label-icon" /></n-icon><label>All related Publication </label>
            </div>
            <div class="tag-list">
                <n-tag :checked="true" class="tag-sty" v-for="(i, index) in pmidShow" :key="i.id" @click="selectRelate(i, index)" :style="i.id === selectedPmid ? 'background: rgba(42, 130, 228, 1); border: 1px solid rgba(42, 130, 228, 1); color: #ffffff' : ''">{{ i.pmid }}</n-tag>
                <!-- <n-tag class="tag-sty add-paper" @click="addNewPaper">Add new paper +</n-tag> -->
                <n-input-group style="width: 120px; margin: 0 5px 5px 0;">
                    <n-input placeholder="Pmid" size="small" style="font-size: 12px;" v-model:value="newPmidValue">
                        <template #suffix>
                            <n-icon class="add-icon" @click="addNewPub" :component="Add" />
                        </template>
                    </n-input>
                </n-input-group>
            </div>
        </div>
        <div class="criteria-content" v-show="!isFullAutomatic">
            <div class="criteria-form-left">
                <div class="criteria-form-group" v-if="pmidShow.length > 0">
                    <n-carousel :loop="false" :show-dots="false" :show-arrow="true" style="position: inherit;" :current-index="currentEvidence">
                        <CriteriaForm v-for="i in evidencesSet" :key="i.id" :info="i" :router="props.router" @delete="getDeleteId" @add="addNewId" />
                        <!-- <div style="width: 100%; height: 600px; background-color: red;"></div> -->
                        <!-- <n-carousel-item :style="{ height: '100%' }" v-for="(i, index) in criteriaFormGroup" :key="index">
                            <CriteriaForm :arg="i"/>
                        </n-carousel-item> -->
                        <!-- <template #dots="{ total, currentIndex, to }">
                            <ul class="custom-dots">
                                <li
                                    v-for="index of total"
                                    :key="index"
                                    :class="{ ['is-active']: currentIndex === index - 1 }"
                                    @click="to(index - 1)"
                                ></li>
                            </ul>
                        </template> -->
                        <template #arrow>
                            <button type="button" class="custom-arrow--left arrow-btn" @click="prev">
                                <n-icon><ArrowBack /></n-icon>
                            </button>
                            <button type="button" class="custom-arrow--right arrow-btn" @click="next">
                                <n-icon><ArrowForward /></n-icon>
                            </button>
                        </template>
                    </n-carousel>
                </div>
                <div class="criteria-form-group" v-show="pmidShow.length === 0">
                    <n-empty description="Empty">
                        <template #extra>
                        </template>
                    </n-empty>
                </div>  
            </div>
        </div>
        <template v-if="isFullAutomatic">
            <FullAutomaticForm :info="fullAutoData" @update="updateFullAuto" />
        </template>
    </div>
</template>

<script setup lang="ts">
    import { h, ref, onMounted } from 'vue';
    import { ReaderOutline, ArrowBack, ArrowForward, Add } from '@vicons/ionicons5';
    import CriteriaForm from './criteriaForm.vue';
    import FullAutomaticForm from './fullAutomaticForm.vue';
    import { Dialog, Message } from "@/utils/utils";
    import { newForm } from '@/static/newForm';
    import PublicationInfor from './publicationInfor.vue';

    enum interpretType { BS1, BS2, BA1, PM2, PVS1, PM1, PP3, BP4, PM4, BP3, BP7, PS1, PM5 };

    const props = defineProps(['info', 'router']);
    const isFullAutomatic = ref<boolean>(props.info.rowData.rule in interpretType);
    const pmidShow = ref<Array<any>>([]);
    const selectedPmid = ref<string | null>('');
    const currentEvidence = ref<number>(0);
    const evidencesSet = ref<Array<any> | null | undefined>(props.info.rowData.evidences.map((v: any)=> {return {...v, name: props.info.rowData['rule'], variantId: props.info.variantId}}));
    const fullAutoData = ref<object | any>(props.info);
    const newPmidValue = ref<string>('');
    const getData = () => {
        if(!props.info.rowData.evidences) return
        // for(let i=0; i<props.info.rowData.evidences.length; i++) {
        //     props.info.rowData.evidences[i].name = props.info.rowData.rule;
        //     props.info.rowData.evidences[i].variantId = props.info.rowData.variantId;
        // }
        // evidencesSet.value = props.info.rowData.evidences;
        // console.log(evidencesSet.value)
        let findIndex = evidencesSet.value?.findIndex(v=> {
            return v.id === null
        })
        if(findIndex !== null && findIndex !== undefined && findIndex > -1) {
            evidencesSet.value?.splice(findIndex, 1);
        }
        selectedPmid.value = props.info.rowData.evidences[0]?.id;
        for (const p of props.info.rowData.evidences) {
            pmidShow.value.push(p);
        };
    }
    const selectRelate = (evidence: any, index: number) => {
        selectedPmid.value = evidence.id;
        currentEvidence.value = index;
    }
    const addNewPub = () => {
        if(!newPmidValue.value) {
            Message.warning('Please input pmid/pmcid/doi');
            return
        }
        const d = Dialog.info({
            title: ()=> h('div', {
                style: { width: '100%', textAlign: 'center', fontWeight: 'bolder', fontSize: '20px' }
            }, { default: () => "Publication information" }),
            showIcon: false,
            style: { 'width': '50%', 'border-radius': '10px' },
            content: ()=> h(PublicationInfor, { info: newPmidValue.value, variantId: '', pmidSet: '', from: 'newAdd', onGetInfor(data: any) {
                addNewPaper(data);
                d.destroy();
            }})
        })
    }
    const addNewPaper = (pmid: string) => {
        let findIndex = evidencesSet.value?.findIndex(v=> {
            return v.id === null
        })
        if(findIndex !== null && findIndex !== undefined && findIndex > -1) {
            Message.warning('There are currently new papers that have not been added');
            return 
        }
        if(!evidencesSet.value) {
            evidencesSet.value = [];
        }
        newForm['pmid'] = pmid;
        newForm['id'] = null;
        newForm['name'] = props.info.rowData['rule'];
        newForm['variantId'] = props.info.variantId;
        evidencesSet.value?.push(newForm); 
        pmidShow.value.push(newForm);
        currentEvidence.value = pmidShow.value.length -1;
        selectedPmid.value = newForm['id'];
    }
    const prev = () => {
        let val = currentEvidence.value;
        currentEvidence.value = val === 0 ? pmidShow.value.length -1 : --val;
        selectedPmid.value = pmidShow.value[currentEvidence.value]['id']
    } 
    const next = () => {
        let val = currentEvidence.value;
        currentEvidence.value = val === pmidShow.value.length -1 ? 0 : ++val;
        selectedPmid.value = pmidShow.value[currentEvidence.value]['id']
    }
    const getDeleteId = (id: string | number) => {
        let findIndex = evidencesSet.value?.findIndex(v=> {
            return v.id === id;
        })
        if(findIndex !== null && findIndex !== undefined && findIndex > -1) {
            evidencesSet.value?.splice(findIndex, 1);
            pmidShow.value.splice(findIndex, 1);
            currentEvidence.value = findIndex > pmidShow.value.length -1 ? 0 : findIndex;
            selectedPmid.value = pmidShow.value[currentEvidence.value]['id'] ? pmidShow.value[currentEvidence.value]['id'] : '';
        }
    }
    const addNewId = (param: object) => {
        // let findIndex = evidencesSet.value?.findIndex(v=> {
        //     return v.id == null;
        // })
        // if(findIndex !== null && findIndex !== undefined && findIndex > -1) {
        //     if(evidencesSet.value) {
        //         pmidShow.value[findIndex] = param;
        //         evidencesSet.value[findIndex] = param;
        //         selectedPmid.value = param['id'];
        //     }   
        // }
        if(evidencesSet.value) {
            pmidShow.value[currentEvidence.value] = param;
            evidencesSet.value[currentEvidence.value] = param;
            selectedPmid.value = param['id'];
        }   
    }
    const updateFullAuto = (data: object) => {
        for(let i in data) {
            fullAutoData.value.rowData[i] = data[i];
        }
    }
    onMounted(() => {
        getData();
    });
</script>

<style scoped lang="scss">
    .criteria-dialog {
        padding: 0 20px;
        position: relative;
        .pmid-list {
            .label-sty {
                display: flex;
                align-items: center;
                .label-icon {
                    width: 18px;
                    height: 18px;
                    color: #205394;
                }
                label {
                    color: #205394;
                    margin-left: 10px;
                }
            }
            .tag-list {
                .tag-sty {
                    border: 1px solid #A6A6A6;
                    border-radius: 3px;
                    background-color: #E5E5E5;
                    margin: 0 5px 5px 0;
                    padding: 0px 3px;
                }
                .add-paper {
                    border: none;
                    border-radius: 3px;
                    background-color: rgba(42, 130, 228, .7);
                    color: #ffffff;
                    padding: 0 10px !important;
                    cursor: pointer;
                    &:hover {
                        background-color: rgba(42, 130, 228, 1);
                    }
                }
            }
        }
        .criteria-content {
            .criteria-form-left {
                width: 100%;
                .criteria-form-group {
                    width: 100%;
                }
            }
            .add-btn {
                width: 200px;
                margin-left: 20px;
                .add-icon {
                    width: 56px;
                    height: 56px;
                    border: 2px solid rgba(42, 130, 228, .5);
                    border-radius: 4px;
                    font-size: 40px;
                    color: rgba(42, 130, 228, .5);
                    text-align: center;
                    line-height: 52px;
                    margin-left: 30px;
                    cursor: pointer;
                }
                .add-icon:hover {
                    border: 2px solid rgba(42, 130, 228, 1);
                    color: rgba(42, 130, 228, 1);
                }
            }
        }
    }
    .carousel-img {
        margin: 0 auto;
        width: 100%;
        height: 100%;
        object-fit: cover;
    } 

    .custom-dots {
        display: flex;
        margin: 0;
        padding: 0;
        position: absolute;
        bottom: 10px;
        right: 40px;
    }

    .custom-dots li {
        display: inline-block;
        width: 12px;
        height: 4px;
        margin: 0 3px;
        border-radius: 4px;
        background-color: rgba(0, 0, 0, 0.4);
        transition: width 0.3s, background-color 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        cursor: pointer;
    }

    .custom-dots li.is-active {
        width: 40px;
        background: #000;
    }

    .custom-arrow {
        display: flex;
        position: absolute;
        bottom: 15px;
        right: 5px;
    }

    .arrow-btn {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: 28px;
        height: 48px;
        margin-right: 12px;
        color: #fff;
        background-color: rgba(0, 0, 0, 0.4);
        border-width: 0;
        border-radius: 8px;
        transition: background-color 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        cursor: pointer;
    }

    .arrow-btn:hover {
        background-color: rgba(0, 0, 0, 1);
    }
    .arrow-btn:active {
        transform: scale(0.95);
        transform-origin: center;
    }
    .custom-arrow--left {
        position: absolute;
        left: -12px;
        top: 50%;
    }
    .custom-arrow--right {
        position: absolute;
        right: -24px;
        top: 50%;
    }
</style>