<template>
    <div class="gnom-ad">
        <h3>Population Frequency</h3>
        <div class="formula-chart">
            <div class="formula">
                <p><span>Exomes Allele Frequency</span> f = 0.0002028</p>
                <p><span>Grpmax Filtering AF</span> f = 0.000203 (South Asian)</p>
            </div>
            <div class="chart">
                <RelateProgress :info="info" inheritance="AR" />
            </div>
        </div>
        <div class="gnom-table">
            <div class="table-switch">
                <n-space>
                    <div>Exomes<n-switch style="padding-left: 8px" v-model:value="exomesValue" @update:value="switchExomesAndGenomes('Exomes')" /></div>
                    <div>Genomes<n-switch style="padding-left: 8px;" v-model:value="genomesValue" @update:value="switchExomesAndGenomes('Genomes')" /></div>
                </n-space>
            </div>
            <div class="table-list">
                <div class="list-sty list-header">
                    <div class="child-sty1" style="text-align: left;text-indent: 40px;">Genetics Ancestry Group</div>
                    <div class="child-sty2" v-show="existOneExpand"></div>
                    <div :class="[existOneExpand ? 'child-sty2' : 'child-sty1']">Allele Count</div>
                    <div class="child-sty1">Allele Number</div>
                    <div class="child-sty1">Number of Homozygotes</div>
                    <div class="child-sty1">Allele Frequency</div>
                </div>
                <div class="list-sty list-body" v-for="(item, index) in gnomadData">
                    <div class="child-sty1 name1">
                        <n-icon :class="[item.expand ? 'open-icon' : 'close-icon']" :component="Triangle" @click="expandRow(index)"></n-icon>
                        <span>{{ item.group }}</span>
                    </div>
                    <div class="child-sty2 sty2-row" v-if="item.expand && existOneExpand">
                        <div v-for="(i) in item.list">{{ i.name }}</div>
                    </div>
                    <div class="child-sty2 sty2-row" v-if="!item.expand && existOneExpand"></div>
                    <div :class="['sty2-row', existOneExpand ? 'child-sty2' : 'child-sty1', item.expand ? '' : 'close-sty']">
                        <div v-for="(i, inx) in item.list" v-show="inx > 0 ? item.expand : true">{{ i.alleleCount }}</div>
                    </div>
                    <div :class="['child-sty1', 'sty2-row', item.expand ? '' : 'close-sty']">
                        <div v-for="(i, inx) in item.list" v-show="inx > 0 ? item.expand : true">{{ i.alleleNumber }}</div>
                    </div>
                    <div :class="['child-sty1', 'sty2-row', item.expand ? '' : 'close-sty']">
                        <div v-for="(i, inx) in item.list" v-show="inx > 0 ? item.expand : true">{{ i.homozygotes }}</div>
                    </div>
                    <div :class="['child-sty1', 'sty2-row', item.expand ? '' : 'close-sty']">
                        <div v-for="(i, inx) in item.list" v-show="inx > 0 ? item.expand : true">{{ i.alleleFreq }}</div>
                    </div>
                </div>
                <div class="list-sty list-body">
                    <div class="child-sty1 name1">
                        <span style="text-indent: 56px;">XX</span>
                    </div>
                    <div class="child-sty2 sty2-row" v-if="existOneExpand"></div>
                    <div :class="['close-sty', existOneExpand ? 'child-sty2' : 'child-sty1']">0</div>
                    <div class="child-sty1">0</div>
                    <div class="child-sty1">0</div>
                    <div class="child-sty1">0</div>
                </div>
                <div class="list-sty list-body">
                    <div class="child-sty1 name1">
                        <span style="text-indent: 56px;">XY</span>
                    </div>
                    <div class="child-sty2 sty2-row" v-if="existOneExpand"></div>
                    <div :class="['close-sty', existOneExpand ? 'child-sty2' : 'child-sty1']">0</div>
                    <div class="child-sty1">0</div>
                    <div class="child-sty1">0</div>
                    <div class="child-sty1">0</div>
                </div>
                <div class="list-sty list-body" style="border-bottom: none;">
                    <div class="child-sty1 name1">
                        <span style="text-indent: 56px; font-weight: bolder;">Total</span>
                    </div>
                    <div class="child-sty2 sty2-row" v-if="existOneExpand"></div>
                    <div :class="['close-sty', existOneExpand ? 'child-sty2' : 'child-sty1']"></div>
                    <div class="child-sty1"></div>
                    <div class="child-sty1"></div>
                    <div class="child-sty1" style="font-weight: bolder;">0</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang='ts'>
    import { ref, reactive } from 'vue';
    import { Triangle } from '@vicons/ionicons5';
    import { Message } from "@/utils/utils";
    import RelateProgress from './submodules/relatedProgress.vue';

    const info = ref({
        rowData: {
            af: '0.00005',
            rule: 'PM2',
            strength: 'Moderate'
        }
    })
    const exomesValue = ref<boolean>(true);
    const genomesValue = ref<boolean>(true);
    const gnomadData = reactive<Array<any>>([
        {
            group: 'African/African American',
            expand: false,
            list: [
                {
                    name: 'OverAll',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XX',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XY',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                }
            ]
        },
        {
            group: 'Ashkenazi Jewish',
            expand: false,
            list: [
                {
                    name: 'OverAll',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XX',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XY',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                }
            ]
        },
        {
            group: 'East Asian',
            expand: false,
            list: [
                {
                    name: 'OverAll',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'Japanese',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'Korean',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'Other East Asian',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XX',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XY',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                }
            ]
        },
        {
            group: 'European(Finnish)',
            expand: false,
            list: [
                {
                    name: 'OverAll',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XX',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XY',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                }
            ]
        },
        {
            group: 'European(Non-Finnish)',
            expand: false,
            list: [
                {
                    name: 'OverAll',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'Bulgarian',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'Estonian',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'North-western European',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'Other non-Finnish European',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'Southern European',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XX',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XY',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
            ]
        },
        {
            group: 'Latino',
            expand: false,
            list: [
                {
                    name: 'OverAll',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XX',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XY',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                }
            ]
        },
        {
            group: 'South Asian',
            expand: false,
            list: [
                {
                    name: 'OverAll',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XX',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XY',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                }
            ]
        },
        {
            group: 'Others',
            expand: true,
            list: [
                {
                    name: 'OverAll',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XX',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                },
                {
                    name: 'XY',
                    alleleCount: '0',
                    alleleNumber: '0',
                    homozygotes: '0',
                    alleleFreq: '0'
                }
            ]
        }
    ])
    const existOneExpand = ref(true);
    const expandRow = (index: number) => {
        let bool = gnomadData[index].expand;
        gnomadData[index].expand = !bool;
        existOneExpand.value = gnomadData.some(v=> {
            return v.expand === true;
        })
    }
    const switchExomesAndGenomes = (type: string) => {
        if(!exomesValue.value && !genomesValue.value) {
            if(type === 'Exomes') {
                exomesValue.value = true;
            }
            if(type === 'Genomes') {
                genomesValue.value = true;
            }
            Message.warning('Both Exomes and Genomes cannot be closed')
        }else{
            
        }
    }

</script>

<style lang='scss' scoped>
    .gnom-ad {
        h3 {
            width: 100%;
            font-size: 16px;
            font-weight: 600;
            color: #205394;
            padding-bottom: 2px;
            text-align: left;
            border-bottom: 1px solid #E5E5E5;
        }
        .formula-chart {
            display: flex;
            align-items: center;
            .formula {
                width: 50%;
                margin-top: 5px;
                p {
                    line-height: 1.8;
                    color: #000000;
                    text-align: left;
                    text-indent: 16px;
                    span {
                        color: #215495;
                        // font-weight: 600;
                    }
                }
            }
            .chart {
                width: 50%;
                margin-top: 15px;
            }
        }
        .gnom-table {
            margin-bottom: 20px;
            .table-switch {
                margin-bottom: 6px;
            }
            .list-sty {
                display: flex;
                align-items: center;
                font-size: 12px;
                .child-sty1 {
                    width: 20%;
                }
                .name1 {
                    display: flex;
                    align-items: center;
                    justify-content: flex-start;
                    .close-icon {
                        margin-right: 4px;
                        margin-left: 40px;
                        transform: rotate(90deg);
                        &:hover {
                            color: #000;
                        }
                    }
                    .open-icon {
                        margin-right: 4px;
                        margin-left: 40px;
                        transform: rotate(180deg);
                        &:hover {
                            color: #000;
                        }
                    }
                }
                .child-sty2 {
                    width: 10%;
                    text-wrap: nowrap;
                }
                .sty2-row {
                    div {
                        border-bottom: 1px solid #E5E5E5;
                        padding: 2px 0;
                    }
                    div:first-child {
                        border-bottom: 1px solid #A6A6A6;
                    }
                    div:last-child {
                        border-bottom: none;
                    }
                }
                .close-sty {
                    div:first-child {
                        border-bottom: none !important;
                    }
                }
            }
            .list-header {
                background-color: #EDF2F7;
                border-bottom: 1px solid #A6A6A6;
                font-weight: bold;
                padding: 5px 0;
            }
            .list-body {
                border-bottom: 1px solid #E5E5E5;
            }
        }
    }
</style>