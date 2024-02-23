<template>
    <n-space vertical>
        <n-layout>
            <n-layout-header>
                <Header></Header>
            </n-layout-header>
            <n-layout-content>
                <div class="aigi-mywork">
                    <div class="mywork-box">
                        <SearchLog v-if="route.query.type === 'work'" />
                        <GroupWork v-if="route.query.type === 'log'" />
                    </div>  
                </div>
            </n-layout-content>
        </n-layout>
    </n-space>
</template>

<script setup lang="ts">
    import Header from "../components/header.vue";
    import SearchLog from './submodules/searchLog.vue'
    import GroupWork from './submodules/groupWork.vue'
    import { useRoute } from 'vue-router'
    const route = useRoute();
</script>

<style scoped lang="scss">
    .aigi-mywork {
        padding: 20px 40px;
        border-top: 1px solid #E5E5E5;
        // background: linear-gradient(to bottom, rgba(31,82,148,1), rgba(31,82,148,0));
        .mywork-box {
            height: 100%;
            border-radius: 8px;
            background-color: #ffffff;
            box-shadow: 0 2px 4px 0 rgba(0, 0, 0, .25);
        }
    }
</style>