<template>
    <div class="search-log">
        <div class="log-com log-header">
            <div class="variant-name">
                <n-input placeholder="Search" style="width: 80%; text-align: left; font-weight: normal;">
                    <template #suffix>
                        <n-icon :component="SearchOutline" />
                    </template>
                </n-input>
            </div>
            <div class="edited-criteria">Edited criteria</div>
            <div class="submitter">Submitter</div>
            <div class="status">Status</div>
            <div class="time">Time</div> 
        </div>
        <div class="log-table">
            <div class="log-table-row" v-for="item in datas">
                <div class="log-com log-tab-sty" v-for="i in item.list">
                    <div class="variant-name">
                        <span>{{ i.variantName }}</span><span>{{ i.variantDetail }}</span>
                    </div>
                    <div class="edited-criteria">
                        <CriteriaCard :info="test1" class="card-style"/>
                        <CriteriaCard :info="test1" class="card-style"/>
                        <!-- <Card :info="'PM3'" :strength="'Strong'" class="card-style"/>
                        <Card :info="'PM3'" :strength="'Strong'" class="card-style"/>
                        <Card :info="'PM3'" :strength="'Strong'" class="card-style"/> -->
                    </div>
                    <div class="submitter">{{ i.submitter }}</div>
                    <div class="status"><span :class="i.status === 'Completed' ? 'complete' : 'uncomplete'">{{ i.status }}</span></div>
                    <div class="time"></div>
                </div>
                <div style="width: 100%; display: flex; flex-direction: row-reverse;">
                    <div style="width: 10%; text-indent: 20px; text-align: center; color: #808080;">{{ item.time }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang='ts'>
    import { ref } from "vue";
    import { SearchOutline } from '@vicons/ionicons5';
    import CriteriaCard from "./criteriaCard.vue";

    const datas = [
        {
            time: '14-Aug',
            list: [
                {
                    variantName: 'SLC26A4:c.716T>A',
                    variantDetail: '(Pathogenic:PS3_Supporting,PM3_Moderate,PP1,PP4,BS1)',
                    criteria: '',
                    submitter: 'Weifeng W',
                    status: 'Completed',
                    time: ''
                }
            ]
        },
        {
            time: '01-Aug',
            list: [
                {
                    variantName: 'GJB2:c.250G>A',
                    variantDetail: '(Pathogenic:PS3_Supporting,PM3_Moderate,PP1,PP4,BS1)',
                    criteria: '',
                    submitter: 'OldFe',
                    status: 'Uncompleted',
                    time: ''
                }
            ]
        },
        {
            time: '28-Jul',
            list: [
                {
                    variantName: 'SLC26A4:c.716T>A',
                    variantDetail: '(Pathogenic:PS3_Supporting,PM3_ModeratePP4,BS1)',
                    criteria: '',
                    submitter: 'LeeChris',
                    status: 'Completed',
                    time: ''
                },
                {
                    variantName: 'SLC26A4:c.716T>A',
                    variantDetail: '(Pathogenic:PS3_Supporting,PM3_Moderate,PP1,PP4,BS1)',
                    criteria: '',
                    submitter: 'Kaikai Lu',
                    status: 'Completed',
                    time: ''
                },
                {
                    variantName: 'SLC26A4:c.716T>A',
                    variantDetail: '(Pathogenic:PS3_Supporting,PP1,PP4,BS1)',
                    criteria: '',
                    submitter: 'LeeChris',
                    status: 'Completed',
                    time: ''
                },
                {
                    variantName: 'SLC26A4:c.716T>A',
                    variantDetail: '(Pathogenic:PS3_Supporting,PP1,PP4,BS1)',
                    criteria: '',
                    submitter: 'Weifeng W',
                    status: 'Completed',
                    time: ''
                },
            ]
        }
    ];

    const test1 = ref({key:'PM3',value:'Moderate'});
</script>

<style lang='scss' scoped>
    .marginTop {
        margin-top: 10px;
    }
    .search-log {
        padding: 10px;
        .log-header {
            color: #808080;
            font-weight: bold;
            align-items: flex-end;
            border-bottom: 1px solid #E5E5E5;
            padding-bottom: 3px;
            font-size: 14px;
        }
        .log-table {
            .log-table-row {
                border-bottom: 1px solid #A6A6A6;
                .log-tab-sty {
                    background-color: rgba(42, 130, 228, 0.05);
                    // line-height: 56px;
                    // margin: 10px 0;
                    padding: 5px 0;
                    margin-top: 10px;
                    align-items: center;
                }
            }
        }
    }
    .log-com {
        width: 100%;
        display: flex;
        // justify-content: space-around;
        text-align: left;
        font-size: 12px;
        .variant-name {
            width: 45%;
            padding-right: 20px;
            padding-left: 6px;
            span:first-child {
                color: #1F5294;
                font-size: 14px;
            }
            span:last-child {
                color: #808080;
            }
        }
        .edited-criteria {
            width: 25%;
            display: flex;
            flex-wrap: wrap;
            padding-right: 20px;
            .card-style {
                margin-right: 4px;
                margin-bottom: 4px;
                // margin: 4px;
            }
        }
        .submitter {
            width: 10%;
            color: #808080;
            padding-right: 20px;
        }
        .status {
            width: 10%;
            padding-right: 20px;
            span {
                width: 108px;
                height: 24px;
                line-height: 24px;
                display: inline-block;
                text-align: center;
                border-radius: 4px;
            }
            .complete {
                color: #ffffff;
                background-color: #12990F;
            }
            .uncomplete {
                color: #D43030;
                background-color: #CCCCCC;
            }
        }
        .time {
            width: 10%;
            text-align: center;
        }
    }
</style>