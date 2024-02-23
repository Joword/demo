<template>
    <div :id="props.key" class="card-size" :style="{ backgroundColor: criteria.color, borderColor: criteria.color }">
        <div style="display: flex;line-height: 20px;color: white;padding-left: 3px;">
            <div style="font-family: HanSansCN-Bold;font-size: 14px;margin-top: 2px;font-weight:bold;">{{ criteria.name }}</div>
        </div>
        <!--<n-dropdown trigger="click" placement="bottom" @select="handleSelect" :options="options">-->
        <div style="display: flex;line-height: 20px;padding-left: 3px;" id="strength" @click="editStrength">
            <div style="line-height: 20px;color: white;">{{ criteria.strength }}</div>
            <!--<div style="flex: 1.2;padding-top: 1px;float: right;">
                <n-icon :component="CaretDownOutline" @click="editStrength" style="font-weight: bold;" size="12" color="white" />
            </div>-->
        </div>
        <!--</n-dropdown>-->
    </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { useSummary } from "@/store";

const isEdit = ref(false);
const summary = useSummary();
//const { strengthMap } = useSummary();
const props = defineProps(['key', 'info', 'strength']);
const criteria = reactive({ name: '', strength: '', tips: '', color: '' });

//const options = [
//    {
//        label: 'VeryStrong',
//        key: 'verystrong'
//    },
//    {
//        label: 'Strong',
//        key: 'strong'
//    },
//    {
//        label: 'Moderate',
//        key: 'moderate'
//    },
//    {
//        label: 'Supporting',
//        key: 'supporting'
//    },      
//    {
//        label: 'Unmet',
//        key: 'unmet'
//    },
//    {
//        label: 'Unset',
//        key: 'unset'
//    },
//]

const matchColor = (criterias: string) => {
    if (props.info.value==="Unmet") {
        return summary.criteriaPalette['unmet'];
    }else{
        switch (criteria.strength) {
            case 'P':
                return summary.criteriaPalette[criterias.split("_")[0].slice(0, 1).toLocaleLowerCase() + "p"]
            case 'M':
                return summary.criteriaPalette[criterias.split("_")[0].slice(0, 1).toLocaleLowerCase() + "m"]
            case 'S':
                return summary.criteriaPalette[criterias.split("_")[0].slice(0, 1).toLocaleLowerCase() + "s"]
            case 'Supporting':
                return summary.criteriaPalette[criterias.split("_")[0].slice(0, 1).toLocaleLowerCase() + "p"]
            case 'Moderate':
                return summary.criteriaPalette[criterias.split("_")[0].slice(0, 1).toLocaleLowerCase() + "m"]
            case 'Strong':
                return summary.criteriaPalette[criterias.split("_")[0].slice(0, 1).toLocaleLowerCase() + "s"]
            case 'VeryStrong':
                return summary.criteriaPalette[criterias.split("_")[0].slice(0, 1).toLocaleLowerCase() + "v"]
                // return summary.criteriaPalette['bv']
            case null:
            case '':
                if(criteria.name === 'BA1') {
                    return summary.criteriaPalette['pv']
                }else{
                    return summary.criteriaPalette['unmet'];
                }
        }
    }
};
const decision = () => {
    criteria.name = props.info.key;
    criteria.strength = props.info.value;
    criteria.color = matchColor(props.info.key);
}
const editStrength = () => {
    switch (isEdit.value) {
        case false:
            isEdit.value = true;
            break;
        case true:
            isEdit.value = false;
            break;
    }
}
//const handleSelect = (key: string) => {
//    criteria.strength = strengthMap[key];
//}

onMounted(() => {
    decision();
})

</script>

<style lang="css">
#strength {
    cursor: pointer;
}

.pointer {
    cursor: pointer;
}

.card-size {
    width: 85px;
    height: 35px;
    font-size: 12px;
    border-radius: 10px;
    border: 1px solid;
    display: flex;
    justify-content: space-evenly;
    flex-direction: column;
    /* margin-left: 10px; */
    padding: 6px;
}

.n-icon:hover {
    color: white;
}

.n-icon {
    cursor: pointer;
}
</style>