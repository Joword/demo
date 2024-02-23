<template>
    <!-- <div style="padding: 20px 0 0 15px;text-align: left;font-size: 20px;">
        <a style="padding: 0 8px;" href="" target="_blank">PMID164660646</a>, <a href="" target="_blank">PMC3525152</a> <br /><br />
        <div style="font-size: 30px;;padding: 0 8px;font-family: HanSansCN-Bold;">{{ t('paper.line1') }}</div><br />
        <iframe ref="article" :src="htmlExample" width="100%" :height="props.size" frameborder="0"></iframe>
    </div>  -->
    <div id="whole-article">
        <div style="font-size: 18px;;padding: 0 10px;font-family: HanSansCN-Bold;">{{ t('paper.line1') }}</div><br />
        <div class="no-article" v-if="pmcid !== 'PMC3525152' && pmcid !== 'PMC3130723'">
            The article does not exist.
        </div>
        <div v-else v-html="htmlFile" class="html-file" @mousedown="cancelLight" @mouseup.stop="highlight($event)"></div>
        <transition name="fade">
            <div ref="attachedDialog" class="article-tip" :style="{left: left + 'px', top: top + 'px'}" v-if="labelDialog" v-click-outside="clickoutsideDialog">
                <n-button-group class="btn-group" size="tiny">
                    <n-button class="btn-sty" @click="markSelectedParagraph" v-if="!labelsMarked">Mark</n-button>
                    <n-button class="btn-sty" @click="unMarkSelectedParagraph" v-else>Unmark</n-button>
                    <n-button class="btn-sty" @click="highlightTextToChat">Chat</n-button>
                    <n-popconfirm :show-icon="false" :positive-text="null" :negative-text="null" @update:show="popoverChange" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                        <template #trigger>
                            <n-button class="btn-sty">Evidences</n-button>
                        </template>
                        <n-button-group class="btn-group1" size="tiny" vertical>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">PM3</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['PM3']" @click.prevent="toCurrentCriteria('PM3', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['PM3']" @click="toCurrentCriteria('PM3', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('PM3')">Add</n-button>
                                </n-button-group>
                            </n-popconfirm>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">BP2</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['BP2']" @click.prevent="toCurrentCriteria('BP2', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['BP2']" @click="toCurrentCriteria('BP2', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('BP2')">Add</n-button>
                                    <!-- <n-button class="btn-sty2" @click="addCurrentCriteria('BP2')">Add</n-button> -->
                                </n-button-group>
                            </n-popconfirm>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">PS2</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['PS2']" @click.prevent="toCurrentCriteria('PS2', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['PS2']" @click="toCurrentCriteria('PS2', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('PS2')">Add</n-button>
                                    <!-- <n-button class="btn-sty2" @click="addCurrentCriteria('PS2')">Add</n-button> -->
                                </n-button-group>
                            </n-popconfirm>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">PP1</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['PP1']" @click="toCurrentCriteria('PP1', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['PP1']" @click.prevent="toCurrentCriteria('PP1', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('PP1')">Add</n-button>
                                    <!-- <n-button class="btn-sty2" @click="addCurrentCriteria('PP1')">Add</n-button> -->
                                </n-button-group>
                            </n-popconfirm>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">BS4</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['BS4']" @click.prevent="toCurrentCriteria('BS4', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['BS4']" @click="toCurrentCriteria('BS4', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('BS4')">Add</n-button>
                                    <!-- <n-button class="btn-sty2" @click="addCurrentCriteria('BS4')">Add</n-button> -->
                                </n-button-group>
                            </n-popconfirm>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">PP4</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['PP4']" @click.prevent="toCurrentCriteria('PP4', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['PP4']" @click="toCurrentCriteria('PP4', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('PP4')">Add</n-button>
                                    <!-- <n-button class="btn-sty2" @click="addCurrentCriteria('PP4')">Add</n-button> -->
                                </n-button-group>
                            </n-popconfirm>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">BP5</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['BP5']" @click.prevent="toCurrentCriteria('BP5', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['BP5']" @click="toCurrentCriteria('BP5', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('BP5')">Add</n-button>
                                    <!-- <n-button class="btn-sty2" @click="addCurrentCriteria('BP5')">Add</n-button> -->
                                </n-button-group>
                            </n-popconfirm>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">PS4</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['PS4']" @click.prevent="toCurrentCriteria('PS4', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['PS4']" @click="toCurrentCriteria('PS4', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('PS4')">Add</n-button>
                                    <!-- <n-button class="btn-sty2" @click="addCurrentCriteria('PS4')">Add</n-button> -->
                                </n-button-group>
                            </n-popconfirm>
                            <n-popconfirm :show-icon="false" placement="right" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                                <template #trigger>
                                    <n-button class="btn-sty1">PS3</n-button>
                                </template>
                                <n-button-group class="btn-group1" size="tiny" vertical>
                                    <n-button class="btn-sty2" v-for="v in criteriaNameSet['PS3']" @click.prevent="toCurrentCriteria('PS3', v.evidenceCount)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button>
                                    <!-- <n-button class="btn-sty2" v-for="v in criteriaNameSet['PS3']" @click="toCurrentCriteria('PS3', v.id)">{{ v.evidenceCount > 1 ? (v.name + '(' + v.evidenceCount + ')') : v.name }}</n-button> -->
                                    <n-button class="btn-sty2" @click.prevent="addCurrentCriteria('PS3')">Add</n-button>
                                    <!-- <n-button class="btn-sty2" @click="addCurrentCriteria('PS3')">Add</n-button> -->
                                </n-button-group>
                            </n-popconfirm>
                            <!-- <n-button class="btn-sty1" @click="toCurrentCriteria('PM3')">PM3</n-button> -->
                        </n-button-group>
                    </n-popconfirm>
                </n-button-group>
            </div>
        </transition>
        <transition name="fade">
            <div ref="attachedDialog" class="article-tip" :style="{left: leftImg + 'px', top: topImg + 'px'}" v-if="imgDialog" v-click-outside="clickoutsideDialog">
                <n-button-group class="btn-group" size="tiny">
                    <n-button class="btn-sty" @click="markImages">{{ imageMark ? 'Mark' : 'Unmark' }}</n-button>
                    <n-popconfirm :show-icon="false" :positive-text="null" :negative-text="null" style="background-color: rgba(252,85,49, .8); padding: 6px 6px 0 6px;" arrow-style="background-color: rgba(252,85,49, .8);">
                        <template #trigger>
                            <n-button class="btn-sty">Evidences</n-button>
                        </template>
                        <n-button-group class="btn-group1" size="tiny" vertical>
                            <n-button class="btn-sty1">PS4</n-button>
                            <n-button class="btn-sty1">PS3</n-button>
                            <n-button class="btn-sty1">PP1</n-button>
                        </n-button-group>
                    </n-popconfirm>
                </n-button-group>
            </div>
        </transition>
    </div>    
</template>

<script setup lang="ts">
    import { ref, watch, onMounted } from "vue";
    import { useI18n } from "vue-i18n";
    import { Dialog } from "@/utils/utils";
    import vClickOutside from "@/directives/clickOutside";
    import Highlighter from 'web-highlighter';
    import axios from "axios";
    import { Message } from "@/utils/utils";
    import { useChatInfo } from '@/store';
    import { paperCriteriaForm } from '@/static/newForm';
    import { useRoute } from "vue-router";
    import { getPaperHighlightShowing, addPaperHighlight, addImgPaperHighlight, deletePapaerHightlight, getPaperCriteriaShowing } from "@/api/request";

    const { t } = useI18n();
    const route = useRoute();

    const pmid = route.query.pmid;
    const pmcid = route.query.pmcid;
    const htmlFile = ref('');
    const attachedDialog = ref<null | HTMLElement>(null);
    const labels = ref<string | undefined | null>('');
    const labelsMarked = ref<boolean>(false);
    const unMarkLabelsId = ref<string>('');
    const startIndex = ref<number>(0);
    const endIndex = ref<number>(0);
    const labelDialog = ref<boolean>(false);
    const imgDialog = ref<boolean>(false);
    const left = ref<number>(0);
    const top = ref<number>(0);
    const leftImg = ref<number>(0);
    const topImg = ref<number>(0);
    const imageObj = ref<any>(null);
    const imageMark = ref<boolean>(false);
    const selection = ref<any>();
    const highlighter = ref<any>(null);
    const clickMark = ref<boolean>(false); 
    const highlightAdd = ref<any>({});
    const popoverIsShow = ref<boolean>(false);
    const criteriaNameSet = ref<object>({});
    const addCriteriaLinkInfo = ref<any>({});
    const saveHighTextSet = ref<any>();

    const chatInfo: any = useChatInfo();

    const getHihglighter = () => {
        getPaperHighlightShowing({userId: 21, pmid: pmcid}).then(res=> {
            return new Promise((resolve) => {
                resolve(res.data);
            })
        }).then((res: any)=> {
            let hss: any = res.data.texts;
            saveHighTextSet.value = hss;
            highlighter.value = new Highlighter({exceptSelectors: ['table']});
            // highlighter.value?.on(Highlighter.event.CREATE, ({sources}: any)=> {
            //     let ss = sources[0];
            // })
            if(!hss || hss.length === 0) return
            hss.forEach(
                (hs: any) => highlighter.value.fromStore(hs.startMeta, hs.endMeta, '', hs.id)
            );
            for (let m = 0; m < highlighter.value.getDoms().length; m++) {
                highlighter.value.getDoms()[m].style.background = "#f1ca17";
                highlighter.value.getDoms()[m].style.cursor = "pointer";
            }
            
            let himgs: any = res.data.imgs;
            imagesHighlight(himgs);
        })
    }
    const getHtmlFile = () => {
        if(pmcid !== 'PMC3525152' && pmcid !== 'PMC3130723') return;
        axios.get(`./publication/${pmcid}.html`, { responseType: 'text' }).then(res=> {
            htmlFile.value = res.data.replace(/<\/?em>/g, '').replaceAll('&gt;', '>');
        }).then(()=> {
            getPaperCriteriaShowing({pmid: pmid, variantId: '7-107315505-T-A', userId: '22'}).then((res: any)=> {
                const { data } = res.data;
                const ner_set: any = [];
                for(let i of ['ner_disease', 'ner_gene', 'ner_variantId']) {
                    let obj: any = {};
                    obj.name = i;
                    obj.value = data[i].split('|').filter((v: any)=> v !== '')
                    ner_set.push(obj) 
                }
                for(let i in ner_set) {
                    ner_set[i].keyWordSet = [];
                    for(let t in ner_set[i].value) {
                        let spl = ner_set[i].value[t].split(',');
                        let count = 0;
                        new Promise((resolve: any, _reject: any)=> {
                            for(let k of spl) {
                                // const regex = new RegExp(k.replace(/[\\s\\S]*?[.*+?^${}()|[\]\\][\\s\\S]*?/g, '\\$&'), "gi");
                                const regex = new RegExp(k.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'), "gi");
                                const matches = htmlFile.value.match(regex);
                                count += matches ? matches.length : 0;
                            }
                            resolve();
                        }).then(()=> {
                            for(let k of spl) {
                                const regex = new RegExp(k.replace(/[\\s\\S]*?[.*+?^${}()|[\]\\][\\s\\S]*?/g, '\\$&'), "gi");
                                if(ner_set[i].name === 'ner_gene') {
                                    htmlFile.value = htmlFile.value.replaceAll(regex, "<mark class='mark-keywords-gene' style='color: #fff !important;'>$&</mark>");
                                }
                                if(ner_set[i].name === 'ner_disease') {
                                    htmlFile.value = htmlFile.value.replaceAll(regex, "<mark class='mark-keywords-disease' style='color: #fff !important;'>$&</mark>");
                                }
                                if(ner_set[i].name === 'ner_variantId') {
                                    htmlFile.value = htmlFile.value.replaceAll(regex, "<mark class='mark-keywords-variant' style='color: #fff !important;'>$&</mark>");
                                }
                            }
                        })
                        ner_set[i].keyWordSet.push({keyName: ner_set[i].value[t], count: count})
                    }
                }
                console.log(ner_set)
                chatInfo.getKeyWordSet(ner_set);
            }).then(()=> {
                getHihglighter();
            })
        })
    }
    const imagesHighlight = (himgs: any) => {
        const element = document.querySelector('.html-file');
        const images = element?.querySelectorAll('img');
        if(images && images.length > 0) {
            images.forEach(image => {
                let imgSrc = image.getAttribute('src');
                if(himgs && himgs.length > 0) {
                    himgs.forEach((src: any) => {
                        if(imgSrc === src) {
                            image.style.border = '2px solid #f1ca17';
                            image.setAttribute('data-mark', 'true');
                        }
                    })
                }
                image.addEventListener('click', function(event: MouseEvent) {
                    event.preventDefault();
                    imgDialog.value = true;
                    const ele = document.getElementById('whole-article') as HTMLElement;
                    const maxWidth = ele.clientWidth;
                    if((maxWidth - event.pageX) < 150) {
                        leftImg.value = event.clientX - 150;
                    }else{
                        leftImg.value = event.clientX;
                    }
                    topImg.value = event.clientY;
                    imageObj.value = image;
                    let attribute = image.getAttribute('data-mark');
                    imageMark.value = attribute ? false : true;
                });
            });
        }
    }
    const markImages = () => {
        let src = imageObj.value?.getAttribute('src');
        let attribute = imageObj.value.getAttribute('data-mark');
        if(attribute) {
            const d = Dialog.warning({
                title: 'Are you sure?',
                content: 'Are you sure unmark?',
                positiveText: 'Sure',
                negativeText: 'Cancel',
                onPositiveClick: () => {
                    deletePapaerHightlight({userId: 21, pmid: pmcid, path: src}).then((res: any)=> {
                        if(res.data.code === 1000) {
                            imageObj.value.style.border = 'none';
                            imageObj.value.removeAttribute('data-mark');
                            d.destroy();
                            Message.success('Unmarked success!');
                        }else{
                            Message.success('Unmarked fail!');
                        }
                    })
                },
                onNegativeClick: () => {}
            })
        }else{
            imageObj.value.style.border = '2px solid #f1ca17';
            imageObj.value.setAttribute('data-mark', 'true');
            imageMark.value = false;
            addImgPaperHighlight([{userId: 1, path: src}]).then((res: any)=> {
                if(res.data.code === 1000) {
                    Message.success('Marked success!');
                }else{
                    imageObj.value.style.border = 'none';
                    imageObj.value.removeAttribute('data-mark');
                    Message.success('Marked fail!');
                }
            })
        }
    }
    const cancelLight = () => {
        if(!window.getSelection()?.isCollapsed) window.getSelection()?.collapseToStart();
    }
    
    const highlight = (event: MouseEvent) => {
        imgDialog.value = false;        
        if(!window.getSelection()?.isCollapsed) { 
            labelDialog.value = true;
            labelsMarked.value = false;
            clickMark.value = true;
            selection.value = window.getSelection();
            labels.value = window.getSelection()?.toString();
            if (selection.value?.rangeCount && selection.value?.rangeCount > 0) {
                const range = selection.value?.getRangeAt(0);
                const preSelectionRange = range.cloneRange();
                preSelectionRange.selectNodeContents(document.getElementsByClassName('html-file')[0] as HTMLElement);
                preSelectionRange.setEnd(range.startContainer, range.startOffset);
                startIndex.value = preSelectionRange.toString().length;
                endIndex.value = startIndex.value + range.toString().length;
            }
        }else{
            labelDialog.value = false;
            labels.value = '';
        }
        const ele = document.getElementById('whole-article') as HTMLElement;
        const maxWidth = ele.clientWidth;
        if((maxWidth - event.pageX) < 150) {
            left.value = event.pageX - 150;
        }else{
            left.value = event.pageX;
        }
        top.value = event.clientY;
    }

    const markSelectedParagraph = () => {
        if(!clickMark.value) return;
        if(!highlighter.value) {
            highlighter.value = new Highlighter({exceptSelectors: ['table']});
        }
        let highter = highlighter.value.fromRange(selection.value?.getRangeAt(0));
        for (let m = 0; m < highlighter.value.getDoms().length; m++) {
            if (highlighter.value.getDoms()[m].dataset.highlightId == highter.id) {
                highlighter.value.getDoms()[m].style.background = "#f1ca17";
                highlighter.value.getDoms()[m].style.cursor = "pointer";
            }
        }
        if(window.getSelection()) window.getSelection()?.collapseToStart();
        clickMark.value = false;
        highlightAdd.value = { startMeta: highter.startMeta, endMeta: highter.endMeta, id: highter.id, text: '' };
        addPaperHighlight([{ userId: 21, pmid: pmcid, ...highlightAdd.value }]).then(res=> {
            if(res.data.code === 1000) {
                Message.success('Mark success!')
                labelsMarked.value = true;
                unMarkLabelsId.value = highter.id;
                saveHighTextSet.value.push(highlightAdd.value);
            }else{
                Message.error('Mark fail!')
                highlighter.value.remove(highter.id);
                labelDialog.value = false;
            }
        })
    }
    const unMarkSelectedParagraph = () => {
        const d = Dialog.warning({
            title: 'Are you sure?',
            content: 'Are you sure unmark?',
            positiveText: 'Sure',
            negativeText: 'Cancel',
            onPositiveClick: () => {
                deletePapaerHightlight({id: unMarkLabelsId.value, userId: 21, pmid: pmcid}).then((res: any)=> {
                    if(res.data.code === 1000) {
                        Message.success('Unmark success!');
                        highlighter.value.remove(unMarkLabelsId.value);
                        saveHighTextSet.value = saveHighTextSet.value.filter((v: any)=> v.id !== unMarkLabelsId.value);
                        d.destroy();
                    }
                })
            },
            onNegativeClick: () => {}
        })
    }
    const highlightTextToChat = () => {
        chatInfo.getHighLightLabels(labels.value as string);
        chatInfo.isOpenChat(true);
        chatInfo.openChatFirst();
        labelDialog.value = false;
    }
    const popoverChange = (value: boolean) => {
        popoverIsShow.value = value;
    }
    const toCurrentCriteria = (name: string, evidenceCount: number)=> {
        let findIndex = chatInfo.criteriaSet.findIndex((v: any)=> {
            return name === v.name && evidenceCount === v.evidenceCount;
        })
        chatInfo.criteriaSet[findIndex].evidence += labels.value;
        chatInfo.criteriaSet[findIndex].isOpen = true;
        chatInfo.criteriaSet[findIndex].updateDisabled = false;
        chatInfo.setCriteriaRelate({ name: name, labels: labels.value });
        chatInfo.isShowOverView(true);
        labelsMarked.value = false;
        labelDialog.value = false;
        imgDialog.value = false;
        setTimeout(()=> {
            let link = document.createElement('a');
            link.href = `#${chatInfo.criteriaSet[findIndex].evidenceCount > 1 ? (chatInfo.criteriaSet[findIndex].name + '(' + chatInfo.criteriaSet[findIndex].evidenceCount + ')') : chatInfo.criteriaSet[findIndex].name}`;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }, 100)
    }
    const addCurrentCriteria = (name: string) => {
        const isSave = chatInfo.criteriaSet.some((v: any)=> v.id === '' && v.name === name);
        const lastIndex = chatInfo.criteriaSet.reduceRight((pre: any, cur: any, index: any)=> {
            return pre === -1 && cur.name === name ? index : pre;
        }, -1)
        if(isSave) {
            addCriteriaLinkInfo.value = { name: name, evidenceCount: chatInfo.criteriaSet[lastIndex].evidenceCount };
            Message.error(`There are unsaved changes ${name}${chatInfo.criteriaSet[lastIndex].evidenceCount > 1 ? '(' + chatInfo.criteriaSet[lastIndex].evidenceCount + ')' : ''}`);
            return
        }
        let count = lastIndex > -1 ? chatInfo.criteriaSet[lastIndex].evidenceCount : 0;
        let addPosition = lastIndex > -1 ? lastIndex + 1 : chatInfo.criteriaSet.length + 1;
        paperCriteriaForm[name.toLowerCase()].evidence = labels.value;
        chatInfo.criteriaSet.splice(addPosition, 0, Object.assign({ name: name, originName: name.toLowerCase(), isOpen: true, updateDisabled: false, evidenceCount: count + 1, baseInfo: {description: '', strength: '' }}, paperCriteriaForm[name.toLowerCase()]))
        addCriteriaLinkInfo.value = { name: name, evidenceCount: count + 1 };
        if(!chatInfo.showOverView) {
            labelsMarked.value = false;
            labelDialog.value = false;
            imgDialog.value = false;
            chatInfo.isShowOverView(true);
        }
        setTimeout(() => {
            let link = document.createElement('a');
            link.href = `#${addCriteriaLinkInfo.value.evidenceCount > 1 ? (addCriteriaLinkInfo.value.name + '(' + addCriteriaLinkInfo.value.evidenceCount + ')') : addCriteriaLinkInfo.value.name}`;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }, 100);
    }

    watch(()=> highlighter.value, ()=> {    
        highlighter.value?.on(Highlighter.event.CLICK, (data: any)=> {
            labelsMarked.value = true;
            labelDialog.value = true;
            unMarkLabelsId.value = data.id;
            for (let m = 0; m < highlighter.value.getDoms().length; m++) {
                if (highlighter.value.getDoms()[m].attributes[1].nodeValue === data.id) {
                    labels.value += highlighter.value.getDoms()[m].textContent.trim();
                }
            }
            labels.value = (labels.value as string).replace(/\s+/g, ' ');
            chatInfo.getHighLightLabels('');
        })
    })
    watch(()=> chatInfo.criteriaSet, ()=> {
        criteriaNameSet.value = {};
        chatInfo.criteriaSet.forEach((v: any)=> {
            if(v.name in criteriaNameSet.value) {
                criteriaNameSet.value[v.name].push(v);
            }else{
                criteriaNameSet.value[v.name] = [v];
            }
        })
    }, {deep: true})
    watch(()=> chatInfo.switchStatus, ()=> {
        const map = {
            gene: 'ner_gene',
            disease: 'ner_disease',
            mutation: 'ner_variantId'
        }
        let findIndex = chatInfo.keyWordSet.findIndex((v: any)=> v.name === map[chatInfo.switchStatus.type]);
        let elements: any = null;
        switch(chatInfo.keyWordSet[findIndex].name) {
            case map.gene:
                elements = document.querySelectorAll('.mark-keywords-gene');
                break;
            case map.disease:
                elements = document.querySelectorAll('.mark-keywords-disease');
                break;
            case map.mutation:
                elements = document.querySelectorAll('.mark-keywords-variant');
                break;
        }
        if(chatInfo.switchStatus.status) {
            elements.forEach((element: any) => {
                element.removeAttribute('style', 'background: transparent !important;');
                element.setAttribute('style', 'color: #fff !important;');
                if(element.children.length > 0) {
                    element.children[0].removeAttribute('style', 'background: #f1ca17 !important;');
                }
            })
        }else{
            elements.forEach((element: any) => {
                element.setAttribute('style', 'background: transparent !important; color: #000 !important;');
                if(element.children.length > 0) {
                    element.children[0].setAttribute('style', 'background: #f1ca17 !important;');
                }
            })
        }
        // for(let t in chatInfo.keyWordSet[findIndex].value) {
        //     let spl = chatInfo.keyWordSet[findIndex].value[t].split(',');
        //     if(chatInfo.switchStatus.status) {
        //         for(let k of spl) {
        //             if(chatInfo.keyWordSet[findIndex].name === 'ner_gene') {
        //                 htmlFile.value = htmlFile.value.replaceAll(k, "<mark class='mark-keywords-gene'>$&</mark>");   
        //             }
        //             if(chatInfo.keyWordSet[findIndex].name === 'ner_disease') {
        //                 htmlFile.value = htmlFile.value.replaceAll(k, "<mark class='mark-keywords-disease'>$&</mark>");
        //             }
        //             if(chatInfo.keyWordSet[findIndex].name === 'ner_variantId') {
        //                 htmlFile.value = htmlFile.value.replaceAll(k, "<mark class='mark-keywords-variant'>$&</mark>");
        //             }
        //         }
        //         setTimeout(()=> {
        //             if(!saveHighTextSet.value || saveHighTextSet.value.length === 0) return
        //             highlighter.value = new Highlighter({exceptSelectors: ['table']});
        //             saveHighTextSet.value.forEach(
        //                 (hs: any) => highlighter.value.remove(hs.id)
        //             );
        //             saveHighTextSet.value.forEach(
        //                 (hs: any) => highlighter.value.fromStore(hs.startMeta, hs.endMeta, '', hs.id)
        //             );
        //             for (let m = 0; m < highlighter.value.getDoms().length; m++) {
        //                 highlighter.value.getDoms()[m].style.background = "#f1ca17";
        //                 highlighter.value.getDoms()[m].style.cursor = "pointer";
        //             }
        //         },10)
                
        //     }else{
        //         let elements: any = null;
        //         if(chatInfo.keyWordSet[findIndex].name === 'ner_gene') {
        //             elements = document.querySelectorAll('.mark-keywords-gene');
        //         }
        //         if(chatInfo.keyWordSet[findIndex].name === 'ner_disease') {
        //             elements = document.querySelectorAll('.mark-keywords-disease');
        //         }
        //         if(chatInfo.keyWordSet[findIndex].name === 'ner_variantId') {
        //             elements = document.querySelectorAll('.mark-keywords-variant');
        //         }
        //         elements.forEach((element: any) => {
        //             // const fragment = document.createDocumentFragment();
        //             // while (element.firstChild) {
        //             //     fragment.appendChild(element.firstChild);
        //             // }
        //             // element.parentNode.replaceChild(fragment, element);
        //             element.setAttribute('style', 'background: transparent !important;');
        //         })
        //     }
        // }
    }, {deep: true})
    function clickoutsideDialog() {
        if(popoverIsShow.value) return
        labelsMarked.value = false;
        labelDialog.value = false;
        imgDialog.value = false;
    }
   
    onMounted(()=> {
        getHtmlFile();
    })
</script>

<style lang="scss">
    #whole-article {
        .no-article {
            text-align: center;
            font-size: 20px;
            font-weight: bolder;
        }
        .html-file {
            padding: 0 10px 10px;
        }
        .article-tip {
            position: fixed;
            z-index: 999;
        }
    }
    .btn-group {
        background-color: rgba(252,85,49, .8);
        .btn-sty {
            color: #ffffff;
            --n-border-hover: rgba(252,85,49, .8) !important;
            --n-border-pressed: rgba(252,85,49, .8) !important;
            --n-border-focus: rgba(252,85,49, .8) !important;
            &:hover {
                color: #000000;;
                background-color: rgba(252,85,49, 1);
            }
        }
    }
    .btn-group1 {
        background-color: rgba(252,85,49, 0);
        .btn-sty1 {
            color: #ffffff;
            --n-border-hover: rgba(252,85,49, .8) !important;
            --n-border-pressed: rgba(252,85,49, .8) !important;
            --n-border-focus: rgba(252,85,49, .8) !important;
            &:hover {
                color: #000000;
                background-color: rgba(252,85,49, 1);
            }
        }
    }
    .btn-sty2 {
        color: #ffffff;
        --n-border-hover: rgba(252,85,49, .8) !important;
        --n-border-pressed: rgba(252,85,49, .8) !important;
        --n-border-focus: rgba(252,85,49, .8) !important;
        &:hover {
            color: #000000;
            background-color: rgba(252,85,49, 1);
        }
        a {
            display: block;
            width: 100%;
            height: 22px;
            line-height: 22px;
            color: #fff;
            text-decoration: none;
        }
    }
    .mark-keywords-gene {
        background-color: #983680;
        // color: #ffffff;
        span {
            background-color: transparent !important;
        }
    }
    .mark-keywords-disease {
        background-color: #FC5531 !important;
        // color: #ffffff;
        span {
            background-color: transparent !important;
        }
    }
    .mark-keywords-variant {
        background-color: #57c3c2;
        // color: #ffffff;
        span {
            background-color: transparent !important;
        }
    }
    .highlight {
        background-color: yellow;
    }
    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 1.5s ease;
    }

    .fade-enter-from,
    .fade-leave-to {
        opacity: 0;
    }
    .fade-enter-to,
    .fade-leave-from {
        opacity: 1;
    }
</style>