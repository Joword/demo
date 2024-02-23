<template>
    <div style="margin-top: 15px;margin-right: 10px;" v-if="props.info==='disease'">
        <n-switch :rail-style="diseaseStyle" size="small" v-model:value="checkboxes.status" @update:value="typeBoxes" :round="false">
            <template #unchecked>
                <span class="checked-style" :style="{ color:summary.diseaseLabel.color }">{{ t('commons.disease') }}</span>
            </template>
            <template #checked>
                <span class="unchecked-style">{{ t('commons.disease') }}</span>
            </template>
        </n-switch>
    </div>
    <div style="margin-top: 15px;margin-right: 10px;" v-else-if="props.info==='gene'">
        <n-switch :rail-style="geneStyle" size="small" v-model:value="checkboxes.status"  @update:value="typeBoxes" :round="false">
            <template #unchecked>
                <span class="checked-style" :style="{ color:summary.geneLabel.color }">{{ t('commons.gene') }}</span>
            </template>
            <template #checked>
                <span class="unchecked-style">{{ t('commons.gene') }}</span>
            </template>
        </n-switch>
    </div>
    <div style="margin-top: 15px;margin-right: 10px;" v-else-if="props.info==='mutation'">
        <n-switch :rail-style="mutationStyle" size="small" v-model:value="checkboxes.status" @update:value="typeBoxes" :round="false">
            <template #unchecked>
                <span class="checked-style" :style="{ color:summary.mutationLabel.color }">{{ t('commons.mutation') }}</span>
            </template>
            <template #checked>
                <span class="unchecked-style">{{ t('commons.mutation') }}</span>
            </template>
        </n-switch>
    </div>
</template>

<script setup lang="ts">
    import { onMounted } from "vue";
    import { useI18n } from "vue-i18n";
    import { useSummary } from "../../store";
    import { reactive, CSSProperties } from "vue";

    const { t } = useI18n();
    const summary = useSummary();

    const props = defineProps(['info']);
    const emit = defineEmits(['switch']);
    const checkboxes = reactive({ status: true, type: props.info });
    const typeBoxes = () => { 
        // switch (arg) {
        //     case "disease":
        //         checkboxes.disease = checkboxes.disease ? false:true
        //         break;
        //     case "gene":
        //         checkboxes.gene = checkboxes.gene ? false:true;
        //         break;
        //     case "mutation":
        //         checkboxes.variant = checkboxes.variant ? false:true;
        //         break;
        // }
        emit('switch', checkboxes)
    }
    const diseaseStyle = ({ focused, checked }:{ focused: boolean; checked: boolean; }) => {
        const style: CSSProperties = {};
        if (checked) {
            style.background = '#FC5531';
            if (focused) {
                style.boxShadow = '0 0 0 2px #f1ca1740';
            }
        } else {
            style.background = "#F5F5F5";
            if (focused) {
                style.boxShadow = '0 0 0 2px #F5F5F540';
            }
        }
        return style;
    }
    const geneStyle = ({ focused, checked }:{ focused: boolean; checked: boolean; }) => {
        const style: CSSProperties = {};
        if (checked) {
            style.background = '#983680';
            if (focused) {
                style.boxShadow = '0 0 0 2px #98368040';
            }
        } else {
            style.background = "#f5f5f5";
            if (focused) {
                style.boxShadow = '0 0 0 2px #f5f5f540';
            }
        }
        return style;
    }
    const mutationStyle = ({ focused, checked }:{ focused: boolean; checked: boolean; }) => {
        const style: CSSProperties = {};
        if (checked) {
            style.background = '#57c3c2';
            if (focused) {
                style.boxShadow = '0 0 0 2px #57c3c240';
            }
        } else {
            style.background = "#f5f5f5";
            if (focused) {
                style.boxShadow = '0 0 0 2px #f5f5f540';
            }
        }
        return style;
    }
    onMounted(() => {
        
    })

</script>

<style lang="css">
    .checked-style {
        font-family: HanSansCN-Bold;
        font-size: 14px;
    }
    .unchecked-style {
        color: whi;
        font-family: HanSansCN-Bold;
        font-size: 14px;
    }
</style>