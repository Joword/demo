<template>
	<n-config-provider :theme="theme">
		<n-config-provider :theme-overrides="themeOverrides">
			<n-global-style />
			<div class="flex flex-col mx-2 sm:mx-4">
				<div class="my-3">
					<PageHeader class="mx-2 sm:mx-2" />
				</div>
				<div class="h-full w-full relative">
					<router-view></router-view>
				</div>
			</div>
		</n-config-provider>
	</n-config-provider>
</template>

<script setup lang="ts">
import PageHeader from './components/PageHeader.vue';
import { darkTheme, GlobalThemeOverrides } from 'naive-ui'
import { useAppStore } from './store';
import { computed } from 'vue';
const appStore = useAppStore();
const themeOverrides: GlobalThemeOverrides = {
	common: {
		primaryColor: '#005bab',
		primaryColorHover: '#0083f7',
		primaryColorPressed: '#0067c3',
		primaryColorSuppl: '#005bab'
	}
}
const theme = computed(() => {
	if (appStore.theme == 'dark') {
		return darkTheme
	} else {
		return {}
	}
})
</script>

<style lang="scss">
//.h-screen {
//    overflow: hidden;
//}
</style>
