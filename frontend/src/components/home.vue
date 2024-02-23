<template #default>
  <div id="homeMain" ref="home">
    <n-row class="home-header">
      <n-col :span="3"></n-col>
      <n-col style="text-align: left;display: flex;align-items: center;" :span="3">
        <svg viewBox="0 0 170 35" focusable="false" class="chakra-icon md:!w-[170px] md:!h-[32px] !w-[89px] !h-[42px] cursor-pointer css-on
					kibi">
          <path fill="white" d="M57.61 17.92c.15 0 .35-.05.51-.05 6.13-.56 10-4.19 10-8.63s-3.78-8-9.86-8.64c-.56 0-1.18-.1-1.79-.1h-47
					A13.77 13.77 0 008 .6c-6.45.87-6.5 7.16-6.5 7.16V35.4H6c15.69 0 15.18-10.07 15.18-10.07v-16s-.52-3 4.64-3h17.94c3.58 0 5.31 2 5.31
					4.45s-1.73 4.44-5.31 4.44H33.18c-7.71 0-8.58 5.73-8.58 5.73h19.16c3.58 0 5.31 2 5.31 4.44s-1.73 4.45-5.31 4.45h-16.3c-9 0-9.55 5.72
					-9.55 5.72h38.62c7 0 11.6-3.93 11.6-8.74 0-4.6-4.14-8.38-10.68-8.68h-.92a3.61 3.61 0 011.08-.22zM163.93.5c-.76 0-1.48.05-2.14.1-9.6
					6.82-10 7-10 7v27.85h5.73s6.38-.05 10.52-2.91a7.44 7.44 0 003.48-6.39V.5zM101.09 6.27H144s0-5.72-9.5-5.72H82.8S76.1.6 76.1 7.3v22.4
					8s0 5.72 9.2 5.72h46.14c12.62 0 12.52-7.72 12.52-7.72V15.16H113.4c-4.39 0-6.69 1.18-7.92 2.51a4.92 4.92 0 00-1.38 3.22h14.05c3.48.1
					5.11 2 5.11 4.44s-1.58 4.35-5.11 4.45H101c-5.73 0-5.27-3.73-5.27-3.73V9.8s-.42-3.53 5.36-3.53z" transform="translate(-1.5 -.5)"></path>
        </svg>
      </n-col>
      <n-col :span="11"></n-col>
      <n-col :span="2" style="text-align: left;">
        <div style="margin-top: 15px;font-size: 18px;" id="header-login" @click="goEvent('name')" v-if="userInfo.userMessage.isLogin">{{ userInfo.userMessage.name }}</div>
        <div style="margin-top: 15px;font-size: 18px;" id="header-login" @click="goEvent('log')" v-else>{{ t('commons.login') }}</div>
      </n-col>
      <n-col :span="2" style="width: 80px;text-align: left;">
        <div style="margin-top: 15px;font-size: 18px;" id="header-register">
          <span class="header-sign-up" @click="goEvent('logout')" v-if="userInfo.userMessage.isLogin">{{ t('commons.logout') }}</span>
          <span class="header-sign-up" @click="goEvent('reg')" v-else>{{ t('commons.register') }}</span>
        </div>
      </n-col>
    </n-row>
    <n-row>
      <n-col :span="3"></n-col>
      <n-col :span="18">
        <div style="text-align: center;margin-top: 100px;font-size: 35px;font-weight: bold;">
          <n-image style="padding-right: 10px;" width="100" src="/transport-green-logo.png" preview-disabled />
          <span style="font-size: 22px;"><b>A</b>ritificial <b>I</b>ntelligence <b>G</b>enome <b>I</b>nterpretation</span>
        </div>
        <div style="font-size: 18px;padding-bottom: 20px;">{{ t('modules.desc4') }}</div>
        <n-auto-complete v-model:value="searchInput" @click="search" @select="handleSelection" @keyup.enter="search" :options="searchOptions" :placeholder="$t('commons.searchPlaceholder')" id="search-input" size="large" clearable>
          <template #suffix>
            <div style="display:flex; justify-content: space-between;">
              <n-select class="ref-selection" :placeholder="$t('commons.inputPlaceholder')" v-model:value="refValue" :options="refOptions" />
              <n-select class="input-selection" :placeholder="$t('commons.inputPlaceholder')" v-model:value="selectValue" :options="options" />
            </div>
            <n-icon style="vertical-align: middle;cursor: pointer;" id="input-suffix" :component="IosSearch" />
          </template>
        </n-auto-complete>
        <div class="example-fontStyle">
          <b>Example</b>:&nbsp; <span id="example2" @click="goLink('gene')">{{ t('example.no_3') }}</span>,&nbsp;
          <span id="example1" @click="goLink('variantId')">{{ t('example.no_1') }}</span>,&nbsp;
          <span id="example3" @click="goLink('pmid')">{{ t('example.no_2') }}</span>
        </div>
      </n-col>
      <n-col :span="3"></n-col>
    </n-row>
    <n-row style="margin-top: 20px;">
      <n-col :span="3"></n-col>
      <n-col :span="18">
        <div class="homecard-sty">
          <div class="card-sty">
            <HomeCard :info="'acmgClassification'"></HomeCard>
          </div>
          <div class="card-sty">
            <HomeCard :info="'interpretation'"></HomeCard>
          </div>
          <div class="card-sty">
            <HomeCard :info="'chatpaper'"></HomeCard>
          </div>
        </div>
      </n-col>
      <n-col :span="3"></n-col>
    </n-row>
  </div>
</template>

<script setup lang="ts">
import {ref, getCurrentInstance, ComponentInternalInstance, onMounted, watch} from 'vue';
    import { useI18n } from 'vue-i18n';
    import { IosSearch } from "@vicons/ionicons4";
import {useInformation, informations} from "@/store";
import {useRouter} from "vue-router";
import {Message} from "@/utils/utils";
import {logout, checkLogin, getLikeVariantId, getLikeGene, getVariantId, getGene} from "@/api/request";

    import HomeCard from "./submodules/homecard.vue";

    const { t } = useI18n();
    const info = useInformation();
    const userInfo = informations();
    const router = useRouter();
    const searchInput = ref("");
const selectValue = ref('ACMG2018');
    const refValue = ref('hg19');

let variantId = /^(chr)?[\dXY]{1,2}-\d+/;
let gene = /^[A-Za-z][A-Za-z0-9]{2,20}$/;

    const { proxy } = getCurrentInstance() as ComponentInternalInstance;
    const options = ref([
		{label: "General", value: 'ACMG2015'},
		{label: "Hearingloss", value: 'ACMG2018'},
		{label: "Others",value: 'ACMG2023'},
    ]);
    const refOptions = ref([
      {label: "hg19", value: 'hg19'},
      {label: "hg38", value: 'hg38'},
    ]);
const searchOptions = ref([]);

    const goEvent = (arg: string) => {
      switch (arg) {
        case "reg":
          router.push({name: 'register'})
          break;
        case "log":
          router.push({name: 'login'});
          break;
        case "name":
          break;
        case "logout":
          // @ts-ignore
          logout().then(res => {
            if (res.data.code === 1000) {
              userInfo.userMessage.isLogin = false;
              userInfo.userMessage.name = "";
              userInfo.userMessage.organization = "";
              router.push({name: 'login'});
              Message.info(t('tips.logoutAgain'));
            }
          })
          break;
      }
    };
const goLink = (arg: string) => {
  switch (arg) {
      // @ts-ignore
    case "gene":
      router.push({name: 'gene', params: {gene: 'GJB2'}});
      break;
      // @ts-ignore
    case "variantId":
      router.push({name: 'variant', params: {variantId: '7-107315505-T-A'}, query: { type: 'info' }});
      break;
      // @ts-ignore
    case "pmid":
      router.push({name: 'pmid', params: {pmid: '25394566'}});
      break;
  }
};
const updateSearchList = (val: string) => {
  if (val.length >= 3) {
    if (variantId.test(val)) {
      const variantArg = new FormData();
      variantArg.append("vd", val);
      getLikeVariantId(variantArg).then(res => {
        searchOptions.value = res.data.data;
      })
    } else if (gene.test(val)) {
      const geneArg = new FormData();
      geneArg.append("g", val);
      getLikeGene(geneArg).then(res => {
        searchOptions.value = res.data.data;
      })
    }
  }
};
const handleSelection = (arg: string) => {
  if (variantId.test(arg)) {
    const variantArg = new FormData();
    variantArg.append("vd", arg);
    getVariantId(variantArg).then(res => {
      if (res.data.code === 1000) {
        router.push({name: 'variant', params: {variantId: arg}});
      }
    })
  } else if (gene.test(arg)) {
    const geneArg = new FormData();
    geneArg.append("g", arg);
    getGene(geneArg).then(res => {
      if (res.data.code === 1000) {
        router.push({name: 'gene', params: {gene: arg}});
      }
    })
  }
};
    const search = () => {
      if (variantId.test(searchInput.value)) {
        const searchVariantArg = new FormData();
        searchVariantArg.append("vd", searchInput.value);
        getVariantId(searchVariantArg).then(res => {
          if (res.data.code === 1000) {
            router.push({name: 'variant', params: {'variantId': searchInput.value}});
          } else if (res.data.code === 3002) {
            Message.error(res.data.msg);
          }
        })
      } else if (gene.test(searchInput.value)) {
        const searchGeneArg = new FormData();
        searchGeneArg.append("g", searchInput.value);
        getGene(searchGeneArg).then(res => {
          if (res.data.code === 1000) {
            router.push({name: 'gene', params: {'gene': searchInput.value}});
          } else if (res.data.code === 3002) {
            Message.error(res.data.msg);
          }
        })
      }
    };

watch(
    () => searchInput.value,
    (value: string) => {
      if (value.length != 0) {
        searchOptions.value = [];
        updateSearchList(value);
      }
    }
)
    
    onMounted(() => {
      if (proxy?.$el.clientWidth > 1900) {
        info.cardSize
      }
      ;
      checkLogin().then(check => {
        if (check.data.code === 1000) {
          userInfo.userMessage.name = check.data.data.username;
          userInfo.userMessage.organization = check.data.data.organization;
          userInfo.userMessage.isLogin = true;
        }
      })
    })

</script>

<style scoped lang="css">
    .home-header {
        height: 60px;
        border-bottom: 1px solid #225BA5;
        border-image: -webkit-linear-gradient(50deg, #225BA5 20%,#deeeff 50%, #225BA5 75%) 1 2 2 1;
        border-image: -moz-linear-gradient(30deg, #225BA5 20%,#deeeff 50%, #225BA5 75%) 2 2 2 2;
        border-image: linear-gradient(30deg, #225BA5 20%,#deeeff 50%, #225BA5 75%) 2 2 2 2;
        .chakra-icon {
            width: 170px;
            height: 35px;
        }
    }
    .n-base-selection .n-base-selection-label{
        border: 1px solid #225BA5 !important;
    }
    #homeMain {
        background-image: url("../assets/backgroud2.png");
        background-repeat: no-repeat;
        background-size: 100% 100%;
        min-height: 850px;
        color: antiquewhite;
    }
    #header-login {
        cursor: pointer;
        min-width: 30px;
    }
    #header-login:hover {
        color: skyblue;
        min-width: 30px;
    }
    #header-register {
        cursor: pointer;
        min-width: 30px;
    }
    #header-register:hover {
        color: skyblue;
        min-width: 30px;
    }
    .example-font {
        font-size: 16px;
        cursor: pointer;
    }
    .example-font:hover {
        color: rgb(16, 123, 223);
    }
    .header-sign-up {
        padding: 5px;
    }
    .header-sign-up:hover {
        padding: 5px;
        border: 1px solid skyblue;
        border-radius: 8px;
    }
    span b {
        color:#00BAAD;
        font-size: 23px;
    }

    .ref-selection {
      width: calc(50% - 5px);
      padding-bottom: 5px;
    }
    .input-selection {
      width: 160px;
        padding-right: 10px;
        padding-bottom: 5px;
    }
    #input-prefix {
        cursor: pointer;
        color: black;
        padding-right: 5px;
    }
    #input-prefix:hover {
        color: #225BA5;
    }
    .n-popover div {
        cursor: pointer;
    }

    #example1 {
      cursor: pointer;
    }
    #example1:hover {
      color: #00BAAD;
    }

    #example2 {
      cursor: pointer;
      font-style: italic;
    }
    #example2:hover {
      color: #00BAAD;
    }

    #example3 {
      cursor: pointer;
    }
    #example3:hover {
      color: #00BAAD;
    }
    #input-suffix:hover {
        color: #225BA5;
    }
    .example-fontStyle {
      font-family: "-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji'";
      float: left;
      margin: 5px 15px;
    }
    .homecard-sty {
      display: flex;
      justify-content: space-around;
    }
    .card-sty {
      width: calc(33.333% - 14px);
      height: 360px;
    }

    #search-input {
      text-align: left;
      border-radius: 20px;
      margin-top: 10px;
    }

    .n-input .n-input-wrapper {
      border-radius: 20px;
    }
</style>