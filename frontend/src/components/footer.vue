<template>
    <n-grid :cols="6" style="background-color: #d4d4d4;min-height: 230px;">
        <n-gi></n-gi>
        <n-gi style="text-align: center;margin: 50px 0 10px 0;">
            <n-image class="pointer" src="/transport-green-logo.png" width="70" preview-disabled/><span class="footer-title" style="margin-left: 10px;">{{ t('commons.contact') }}</span>
            <div class="footer-click"><a href="mailto:pengjiguang@bgi.com" target="_blank" rel="noopener noreferer">{{ t('people.genetic') }}</a></div>
        </n-gi>
        <n-gi style="text-align: center;margin: 50px 0 10px 0;">
            <div class="footer-title">{{ t('commons.learn') }}</div>
            <div class="footer-click">{{ t('commons.aboutAIGI') }}</div>
            <div class="footer-click">{{ t('commons.faq') }}</div>
            <div class="footer-click">{{ t('commons.ACMG_guideline') }}</div>
        </n-gi>
        <n-gi style="text-align: center;margin: 50px 0 10px 0;">
            <div class="footer-title">{{ t('commons.tools') }}</div>
            <div class="footer-link"><a href="http://aleph.genetics.bgi.com/" target="_blank" rel="noopener noreferer">{{ t('commons.aleph') }}</a></div>
            <div class="footer-link"><a href="http://hearing.genetics.bgi.com/" target="_blank" rel="noopener noreferer">{{ t('commons.viphl') }}</a></div>
            <div class="footer-link"><a href="https://autopvs1.bgi.com/" target="_blank" rel="noopener noreferer">{{ t('commons.autopvs1') }}</a></div>
        </n-gi>
        <n-gi style="text-align: center;margin: 50px 0 10px 0;">
            <div class="footer-title">{{ t('commons.link') }}</div>
            <div class="footer-link"><a href="https://www.acmg.net/" target="_blank" rel="noopener noreferer">{{ t('commons.ACMG') }}</a></div>
            <div class="footer-link"><a href="https://www.ncbi.nlm.nih.gov/clinvar/" target="_blank" rel="noopener noreferer">{{ t('commons.ClinVar') }}</a></div>
            <div class="footer-link"><a href="https://pubmed.ncbi.nlm.nih.gov/" target="_blank" rel="noopener noreferer">{{ t('commons.PubMed') }}</a></div>
        </n-gi>
        <n-gi></n-gi>
    </n-grid>
</template>

<script setup lang="ts">
    import { useI18n } from "vue-i18n";
    const { t } = useI18n();
</script>

<style lang="css">
    .pointer {
        cursor: pointer;
    }
    .footer-title {
        font-size: 20px;
        line-height: 20px;
        margin-bottom: 10px;
        font-family: "HanSansCN-Bold";
    }
    .footer-link {
        font-size: 17px;
        margin-bottom: 5px;
        cursor: pointer;
    }
    .footer-link a:hover {
        color: #2f7bdf;
    }
    .footer-click {
        font-size:17px;
        margin-bottom: 5px;
        cursor: pointer;
    }
    .footer-click:hover {
        color: #2f7bdf;
    }
</style>
