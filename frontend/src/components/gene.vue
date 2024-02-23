<template>
  <div id="genes">
    <InfoBar type="gene" :value="geneSymbol" :info="geneBasic"></InfoBar>
    <div style="min-height: 900px;display: flex;">
      <div style="width:40px"></div>
      <div style="flex: 18;">
        <n-divider title-placement="left">Gene summary</n-divider>
        <div style="display: flex;">
          <div style="flex: 1"></div>
          <div style="font-size: 18px;flex: 8;border-radius: 8px;background-color: #d3d3d3;width: 70%;text-align: left;padding: 10px;height: 100px;">
            Mutations in this gene are associated with Pendred syndrome, the most common form of syndromic deafness, an autosomal-recessive disease. It is highly homologous to the SLC26A3 gene; they have similar genomic structures and this gene is located 3' of the SLC26A3 gene. The encoded protein has homology to sulfate transporters. [provided by RefSeq, Jul 2008]
          </div>
          <div style="flex: 1"></div>
        </div>
        <n-divider title-placement="left">{{ t('commons.geneTable') }}&nbsp;<b style="color:black">{{ geneSymbol }}</b>
        </n-divider>
        <div>
          <div style="display: flex;margin-bottom: 10px;">
            <div style="flex: 12;"></div>
            <div style="float: right;flex: 0.5;margin-top: 4px;">{{ t('commons.search') }}:&nbsp;</div>
            <n-input placeholder="variantId, chgvs, phgvs, criteria" style="flex: 2;text-align: left;" clearable></n-input>
            <div style="width: 40px;"></div>
          </div>
          <VariantInformation :info="geneInfo"></VariantInformation>
        </div>
        <n-divider title-placement="left">Variants browser</n-divider>
        <Browser></Browser>
        <n-divider title-placement="left">Classified variants</n-divider>
        <div style="width: 100%;align-items: center;">
          <HeatTable></HeatTable>
        </div>
      </div>
      <div style="width:40px"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted} from "vue";
import {useI18n} from "vue-i18n";
import {useRoute} from "vue-router";
import {useInformation} from "@/store";
import {getGene} from "@/api/request";
import {VariantRows} from "@/types/info";

import InfoBar from "@/components/submodules/infobar.vue";
import HeatTable from "@/components/submodules/heattable.vue";
import VariantInformation from "@/components/submodules/variantInfo.vue";
import Browser from "@/components/submodules/browser.vue"


const {t} = useI18n();
const route = useRoute();
const geneMessages = useInformation();
const geneSymbol = ref(route.params.gene.toString());
const geneBasic = ref({name: '', chromosome: '', symbol: ''});
const geneInfo = ref<VariantRows[]>([]);

onMounted(() => {
  const data = new FormData();
  data.append("g", geneSymbol.value);
  getGene(data).then(res => {
    if (res.data.code === 1000) {
      geneBasic.value.name = res.data.data.geneName;
      geneBasic.value.chromosome = res.data.data.chromosome;
      geneBasic.value.symbol = res.data.data.symbol;
      geneInfo.value = res.data.data.geneInfoVo.slice(0, 5);
      geneMessages.geneInfo.symbol = res.data.data.symbol;
      geneMessages.geneInfo.chromosome = res.data.data.chromosome;
      geneMessages.geneInfo.geneName = res.data.data.geneName;
      geneMessages.geneInfo.info = res.data.data.geneInfoVo.slice(0, 5);
    }
  });
});
</script>

<style scoped>
    .n-divider {
        color:#A6A6A6;
        font-family: HanSansCN-Bold;
    }
</style>