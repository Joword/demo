<template>
    <div class="group-work">
        <div class="work-com work-header">
            <div class="type">Type</div>
            <div class="center"></div>
            <div class="Date">Date</div>
        </div>
        <div class="work-table">
            <div class="work-table-row" v-for="item in datas">
                <div class="work-com work-table-sty" :class="i.type == 'Gene' ? 'geneBgd' : i.type == 'Mutation' ? 'mutationBgd' : i.type == 'Publication' ? 'publicationBgd' : ''" v-for="i in item.list" >
                    <div class="type">
                        <span :class="i.type == 'Gene' ? 'gene' : i.type == 'Mutation' ? 'mutation' : i.type == 'Publication' ? 'publication' : ''">{{ i.type }}</span>
                    </div>
                    <div class="center">
                        {{ i.typeName }}
                    </div>
                    <div class="Date"></div>
                </div>
                <div style="width: 100%; display: flex; padding-left: 45px; flex-direction: row-reverse;">
                    <div style="width: 10%; text-align: left; color: #808080;">{{ item.time }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang='ts'>

    const datas = [
        {
            time: '14-Aug',
            list: [
                {
                    type: 'Gene',
                    typeName: 'GJB2'
                }
            ]
        },
        {
            time: '01-Aug',
            list: [
                {
                    type: 'Mutation',
                    typeName: 'SLC26A4:c.716T>A'
                }
            ]
        },
        {
            time: '01-Aug',
            list: [
                {
                    type: 'Publication',
                    typeName: 'PMID16460646'
                }
            ]
        },
        {
            time: '28-Jul',
            list: [
                {
                    type: 'Gene',
                    typeName: 'GJB2'
                },
                {
                    type: 'Gene',
                    typeName: 'GJB2'
                },
                {
                    type: 'Gene',
                    typeName: 'GJB2'
                },
                {
                    type: 'Gene',
                    typeName: 'GJB2'
                },
            ]
        }
    ]

</script>

<style lang='scss' scoped>
    .group-work {
        padding: 10px;
        .work-header {
            color: #808080;
            font-weight: bold;
            align-items: flex-end;
            border-bottom: 1px solid #E5E5E5;
            padding-bottom: 3px;
            font-size: 14px;
        }
        .work-table {
            .work-table-row {
                border-bottom: 1px solid #A6A6A6;
                .work-table-sty {
                    padding: 5px 0;
                    margin-top: 10px;
                    align-items: center;
                }
                .geneBgd {
                    background-color: rgba(121, 72, 234, 0.05);
                }
                .mutationBgd {
                    background-color: rgba(0, 186, 173, 0.05);
                }
                .publicationBgd {
                    background-color: rgba(42, 130, 228, 0.05);
                }
            }
        }
        .work-com {
            display: flex;
            text-align: left;
            font-size: 14px;
            .type {
                width: 10%;
                padding-right: 20px;
                padding-left: 6px;
                span {
                    display: inline-block;
                    width: 88px;
                    height: 20px;
                    line-height: 20px;
                    border-radius: 5px;
                    text-align: center;
                }
                .gene {
                    background-color: rgba(121, 72, 234, 0.18);
                    color: rgba(121, 72, 234, 1);
                    border: 1px solid rgba(121, 72, 234, 1);
                }
                .publication {
                    background-color: rgba(42, 130, 228, 0.08);
                    color: rgba(42, 130, 228, 1);
                    border: 1px solid rgba(42, 130, 228, 1);
                }
                .mutation {
                    background-color: rgba(0, 186, 173, 0.12);
                    color: rgba(0, 186, 173, 1);
                    border: 1px solid rgba(0, 186, 173, 1);
                }
            }
            .center {
                width: 80%;
                padding-right: 20px;
                color: #1F5294;
            }
            .time {
                width: 10%;
            }
        }
    }
</style>