<template>
    <div :id="props.type" v-if="props.type==='revel'">
        <div>
          <div style="padding-left: 4px;position: relative;height: 44px;">
            <div :style="{ position: 'absolute', paddingLeft: '10px' }">
                <CriteriaCard :info="bp4Test"></CriteriaCard>
            </div>
            <div :style="{ position: 'absolute',marginLeft: '230px' }">
                <CriteriaCard :info="pp3Test"></CriteriaCard>
            </div>
            </div>
            <div style="position: relative;height: 30px;">
              <div :style="{ width: '62px', borderRightStyle: 'solid',height: '25px', borderWidth: '2px', position: 'absolute', marginTop:'5px' }"></div>
              <div :style="{ width: '282px', borderRightStyle: 'solid',height: '25px', borderWidth: '2px', position: 'absolute', marginTop:'5px' }"></div>
            </div> 
        </div>
        <div id="revelBar">
            <div class="revel-bar-full"></div>
            <div class="revel-bar-part">0.936</div>
        </div>
        <div id="barNumber">
            <div class="revel-bar-start">0</div>
            <div class="revel-bar-end">1</div>
            <div :style="{ width: '75px', textAlign:'right', fontFamily:'HanSansCN-Bold', position:'absolute' }">0.15</div>
            <div :style="{ width: '295px', textAlign:'right', fontFamily:'HanSansCN-Bold', position:'absolute' }">0.70</div>
        </div>
    </div>
    <div :id="props.type" v-if="props.type==='regular'">
      <div style="height: 30px;margin-top: 45px;display: flex;">
            <div :style="{ width: '60px',height: '30px', fontFamily:'HanSansCN-Bold',color:'#0CA106' }">1 Benign</div>
            <div :style="{ width: '140px',height: '30px', fontFamily:'HanSansCN-Bold',color:'#DBD7D7' }">9 VUS</div>
            <div :style="{ width: '200px',height: '30px', fontFamily:'HanSansCN-Bold',color:'#D43030' }">32 Deleterious</div>
        </div> 
        <div style="display: flex;">
            <div :style="{ width: '60px', backgroundColor: '#0CA106', opacity: 0.7, height: '20px',border:'1px solid #0CA106',borderTopLeftRadius: '10px', borderBottomLeftRadius:'10px' }"></div>
            <div :style="{ width: '140px', backgroundColor: '#DBD7D7', opacity: 0.7, height: '20px',border:'1px solid #DBD7D7' }"></div>
            <div :style="{ width: '200px', backgroundColor: '#D43030', opacity: 0.7, height: '20px',border:'1px solid #D43030',borderTopRightRadius: '10px', borderBottomRightRadius:'10px' }"></div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { ref, reactive, onMounted } from "vue";
    import CriteriaCard from "./criteriaCard.vue";

    const props = defineProps(['type']);
    const barShow = reactive({ name: '', });
    const bp4Test = ref({key:'BP4',value:'Unmet'});
    const pp3Test = ref({key:'PP3',value:'Supporting'});

    const getTypes = () => {
        switch (props.type) {
            case 'revel':
                barShow.name = props.type;
                break;
            case 'regular':
                barShow.name = props.type;
                break;
        }
    };
    onMounted(() => {
        getTypes();
    })
</script>

<style lang="css">
    #revelBar {
        position: relative;
    }
    #barNumber {
        position: relative;
    }
    #revelDoor {
        position: relative;
    }
    .revel-bar-full {
        height: 20px;
        width: 400px;
        background-color: white;
        border-radius: 10px;
        border: 1px solid black;
        position: absolute;
        opacity: 0.3;
    }
    .revel-bar-part{
        height: 22px;
        width: 380px;
        border-radius: 10px;
        /* border: 1px solid black; */
        opacity: 0.6;
        text-align: right;
        padding-right: 1px;
        color:white;
        font-family: HanSansCN-Bold;
        background-image: linear-gradient(to right, green, red);
    }
    .revel-bar-start {
        position: absolute;
        font-family: HanSansCN-Bold;
    }
    .revel-bar-end {
        position: absolute;
        width: 402px;
        text-align: right;
        font-family: HanSansCN-Bold;
    }
    .revel-bar-ptr {
        position: absolute;
        width: 382px;
        text-align: right;
        font-family: HanSansCN-Bold;
    }
</style>