<template>
    <div class="publication-infor">
        <n-spin :show="loading" :rotate="false">
            <template #icon>
                <n-icon>
                    <BounceLoader
                        :loading="loading"
                        color="#0954AB"
                        size="60px" />
                </n-icon>
            </template>
            <n-scrollbar style="height: 100%;">
                <n-form ref="formRef" size="small" label-placement="left" label-width="140" :disabled="isDisableEdit" :model="pubInforData" :rules="rules">
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="quick search" path="" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="DOI,PMID,Title" style="font-size: 12px;" v-model:value="searchValue" :disabled="props.from === 'newAdd' ? false : isDisableEdit" />
                            <n-button type="info" :disabled="props.from === 'newAdd' ? false : isDisableEdit" @click="()=> getPmidInfo(searchValue)">Search</n-button>
                        </n-input-group>
                    </n-form-item>
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="Pmid" path="pmid" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="PMID" style="font-size: 12px;" v-model:value="pubInforData.pmid" disabled />
                        </n-input-group>
                    </n-form-item>
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="DOI" path="" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="recommand, eg: 10.1126/science.aba208" style="font-size: 12px;" v-model:value="pubInforData.doi" />
                        </n-input-group>
                    </n-form-item>
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="Year" path="" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="Year" style="font-size: 12px;" v-model:value="pubInforData.date" />
                        </n-input-group>
                    </n-form-item>
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="Title" path="title" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="title" style="font-size: 12px;" v-model:value="pubInforData.title" />
                        </n-input-group>
                    </n-form-item>
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="Authors" path="" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="Authors" style="font-size: 12px;" v-model:value="pubInforData.firstAuthor" />
                        </n-input-group>
                    </n-form-item>
                    <!-- <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="Journal(abbr)" path="" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="Journal(abbr)" style="font-size: 12px;" />
                        </n-input-group>
                    </n-form-item> -->
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="URL" path="" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="URL" style="font-size: 12px;" v-model:value="pubInforData.url" />
                        </n-input-group>
                    </n-form-item>
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="Type" path="" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="Type" style="font-size: 12px;" v-model:value="pubInforData.type" />
                        </n-input-group>
                    </n-form-item>
                    <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="Other information" path="" >
                        <n-input-group style="width: 94%;">
                            <n-input placeholder="Other information" style="font-size: 12px;" v-model:value="pubInforData.remark" />
                        </n-input-group>
                    </n-form-item>
                </n-form>
                <div :class="props.from === 'newAdd' ? 'pub-infor-btn1' : 'pub-infor-btn2'">
                    <n-button v-if="props.from !== 'newAdd'" class="btn-sty" type="default" size="small" @click="editInformation">{{ isDisableEdit ? 'Edit' : 'Cancel' }}</n-button>
                    <n-button class="btn-sty" type="info" size="small" @click="confirmInformation">Confirm</n-button>
                </div>
            </n-scrollbar>
        </n-spin>
    </div>
</template>

<script setup lang='ts'>
    import { ref, reactive, onMounted } from 'vue';
    import { FormInst } from 'naive-ui';
    import { BounceLoader } from "vue3-spinner";
    import { Message } from "@/utils/utils";
    import { getPublicationSearch, getPublicationUpdate } from "@/api/request";

    const props = defineProps(['info', 'variantId', 'pmidSet', 'from']);
    const emit = defineEmits(['getInfor']);
    const formRef = ref<FormInst | null>(null);

    const loading = ref(true);
    const isDisableEdit = ref<boolean>(true);
    const searchValue = ref<string>(props.info);
    const isExistPmid = ref<boolean>(false);
    const pubInforData = reactive({
        pmid: '',
        doi: '',
        date: '',
        title: '',
        firstAuthor: '',
        url: '',
        type: '',
        remark: '',
        variantId: ''
    });

    const rules = {
        pmid: {
            required: true,
            message: 'Please input pmid',
            trigger: 'blur'
        },
        title: {
            required: true,
            message: 'Please input title',
            trigger: 'blur'
        }
    }

    const getPmidInfo = (id: string) => {
        loading.value = true;
        getPublicationSearch(id).then(res=> {
            if (res.data.code===1000) {
                if(res.data.data) {
                    for(let i in res.data.data) {
                        pubInforData[i] = res.data.data[i];
                    }
                    isExistPmid.value = true;
                }else{
                    Message.error('The search result does not exist');
                    isExistPmid.value = false;
                }
                loading.value = false;
            }
        })
    }

    const editInformation = () => {
        let value = isDisableEdit.value;
        isDisableEdit.value = !value;
    }
    const confirmInformation = () => {
        if(!isExistPmid.value) {
            Message.error('The literature does not exist');
            return
        }
        if(props.from) {
            emit('getInfor', pubInforData.pmid);
            return 
        }
        if(props.pmidSet.value.includes(pubInforData.pmid)) {
            Message.warning('A literature with this PMID already exists');
            return
        }
        formRef.value?.validate((errors)=> {
            if (!errors) {
                pubInforData.variantId = props.variantId;
                getPublicationUpdate(JSON.parse(JSON.stringify(pubInforData))).then(res=> {
                    if(res.data.code === 1000) {
                        emit('getInfor', pubInforData);
                        Message.success('Submitted successfully');
                    }
                })
            }else{
                Message.warning('Please fill in the required information')
            }
        })
    }

    onMounted(()=> {
        getPmidInfo(props.info);
    })

</script>

<style lang='scss' scoped>
    .publication-infor {
        .pub-infor-btn1 {
            width: calc(100% - 171px);
            margin-left: 141px;
            text-align: right;
            .btn-sty {
                width: 80px;
            }
        }
        .pub-infor-btn2 {
            width: calc(100% - 171px);
            margin-left: 141px;
            display: flex;
            justify-content: space-between;
            .btn-sty {
                width: 80px;
            }
        }
    }
</style>