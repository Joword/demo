<template>
	<n-page-header>
		<template #title>
			<n-space v-if="userStore.user">
				<font class="text-gray-500 inline-block" size="2">{{ $t("commons.siteTag") }}</font>
			</n-space>
		</template>
		<template #avatar>
			<!-- <n-avatar v-if="userStore.user" :src="chatbgiIcon" /> -->
			<a v-if="userStore.user" href="/conversation" style="display: inline-block; line-height: 0;"><n-image
					preview-disabled height="18" :src="chatbgiIcon1" /></a>
			<!-- <a v-if="!userStore.user" href="/login" style="display: inline-block; line-height: 0;"><n-image preview-disabled height="18" :src="chatbgiIcon1" /></a> -->
		</template>
		<template #extra>
			<n-space>
				<div class="inline sm:hidden">
					<n-popover style="padding:0" placement="bottom-start" trigger="click">
						<template #trigger>
							<n-button text><n-icon size="25">
									<Menu />
								</n-icon></n-button>
						</template>
						<n-menu class="max-h-120 overflow-y-auto" :options="menuOptions"
							v-model:value="currentConversationId" @update:value="handleUpdateExpandedKeys" :indent="15" />
					</n-popover>
				</div>
				<div class="hidden sm:inline">
					<div v-if="userStore.user" class="inline-block">
						<span class="hidden sm:inline mr-1">Hi, {{ userStore.user.nickname }}</span>
						<n-dropdown :options="options" placement="bottom-start">
							<n-button circle class="ml-2"><n-icon :component="SettingsSharp" /></n-button>
						</n-dropdown>
					</div>
					<div v-else class="text-gray-500 inline-block">{{ $t("commons.notLogin") }}</div>
					<n-button circle class="ml-2" @click="toggleTheme">
						<n-icon :component="themeIcon" />
					</n-button>
					<n-button v-if="userStore.user?.is_superuser" circle class="ml-2" @click="jumpToAdminOrConv">
						<n-icon :component="isInAdmin ? ChatFilled : ManageAccountsFilled" />
					</n-button>
					<n-dropdown :options="languageOptions" placement="bottom-start">
						<n-button circle class="ml-2">
							<n-icon :component="Language" />
						</n-button>
					</n-dropdown>
					<n-dropdown v-if="userStore.user" :options="isInAdmin ? helpOptions2 : helpOptions1"
						placement="bottom-start">
						<n-button circle class="ml-2">
							<n-icon :component="HelpSharp" />
						</n-button>
					</n-dropdown>
				</div>
			</n-space>
		</template>
	</n-page-header>
</template>

<script setup lang="ts">
import { useConversationStore, useUserStore, useAppStore } from '@/store';
import { SettingsSharp, LogoGithub, Language, Menu, BookOutline, Contrast, HelpSharp } from '@vicons/ionicons5';
import { DarkModeRound, LightModeRound, ChatFilled, ManageAccountsFilled } from '@vicons/material';
import { useI18n } from 'vue-i18n';
import { Dialog, Message } from '@/utils/tips';
import router from '@/router';
import { useRoute } from 'vue-router';

import { DropdownOption, MenuOption, NIcon, NEllipsis } from "naive-ui"
import { ref, computed, h, Component, watch } from 'vue';
import UserProfileCard from './UserProfileCard.vue';
import { popupResetUserPasswordDialog } from '@/utils/renders';
import { resetUserPasswordApi } from '@/api/user';
import { ConversationSchema } from '@/types/schema';

const { t } = useI18n();
const userStore = useUserStore();
const appStore = useAppStore();
const route = useRoute();
const conversationStore = useConversationStore();

const isInAdmin = computed(() => {
	return route.path.startsWith('/admin');
});

const themeIcon = computed(() => {
	if (appStore.theme == 'dark') {
		return DarkModeRound
	} else {
		return LightModeRound
	}
})

const toggleTheme = () => {
	appStore.toggleTheme();
}

function renderIcon(icon: Component) {
	return () => h(NIcon, null, { default: () => h(icon) })
}

const newConversation = ref<ConversationSchema | null>(null);
const currentConversationId = ref<string | null>(null);

const menuOptions = computed(() => {
	// 根据 created_time 降序排序
	const sorted_conversations = conversationStore.conversations?.sort((a: ConversationSchema, b: ConversationSchema) => {
		// return a.create_time - b.create_time;
		if (!a.create_time) return -1;
		if (!b.create_time) return 1;
		const date_a = new Date(a.create_time), date_b = new Date(b.create_time);
		return date_b.getTime() - date_a.getTime();
	});
	const results = sorted_conversations?.map((conversation: ConversationSchema) => {
		return {
			label: () =>
				h(NEllipsis, null, { default: () => conversation.title }),
			key: conversation.conversation_id,
			// disabled: loading.value == true,
			// extra: () => dropdownRenderer(conversation, handleDeleteConversation, handleChangeConversationTitle)
		}
	});
	if (newConversation.value) {
		results?.unshift({
			label: newConversation.value.title,
			key: newConversation.value.conversation_id,
			// disabled: loading.value == true,
		});
	};
	const opt = [
		{
			label: t("commons.setting"),
			key: 'setting',
			icon: renderIcon(SettingsSharp),
			show: userStore.user ? true : false,
			children: [
				{
					label: t("commons.resetPassword"),
					key: 'resetPassword',
				},
				{
					label: t("commons.userProfile"),
					key: 'profile',
				},
				{
					label: t("commons.logout"),
					key: 'logout',
				}
			]
		},
		{
			label: t("commons.theme"),
			key: 'theme',
			icon: renderIcon(Contrast),
		},
		{
			label: t("commons.language"),
			key: 'language',
			icon: renderIcon(Language),
		},
		{
			label: t("commons.newConversation"),
			key: 'new_conversation',
			show: userStore.user ? true : false,
		},
		{
			label: t("commons.history"),
			key: 'conv',
			show: userStore.user ? true : false,
			children: results
		}
	]
	return opt;
});


const handleUpdateExpandedKeys = (key: string) => {
	if (key == "resetPassword") {
		popupResetUserPasswordDialog(
			async (oldpassword: string, newpassword: string) => {
				await resetUserPasswordApi(userStore.user!.id, oldpassword, newpassword);
			},
			() => { Message.info(t("tips.resetUserPasswordSuccess")) },
			() => { Message.error(t("tips.resetUserPasswordFailed")) }
		);
		forgiveSelect();
	} else if (key == "profile") {
		Dialog.info({
			title: t("commons.userProfile"),
			content: () => h(UserProfileCard, {}, {}),
			// positiveText: t("commons.edit"),
			negativeText: t("commons.close"),
			// onPositiveClick: () => { router.push({ name: 'editProfile' }) }
		});
		forgiveSelect();
	} else if (key == "logout") {
		Dialog.info({
			title: t("commons.logout"),
			content: t("tips.logoutConfirm"),
			positiveText: t("commons.confirm"),
			negativeText: t("commons.cancel"),
			onPositiveClick: () => userStore.logout().then(() => {
				Message.success(t('commons.logoutSuccess'));
				router.push({ name: 'login' });
			}),
		});
		forgiveSelect();
	} else if (key == "theme") {
		appStore.toggleTheme();
		forgiveSelect();
	} else if (key == "language") {
		if (appStore.language == 'zh-CN') {
			appStore.setLanguage('en-US');
			forgiveSelect();
		} else {
			appStore.setLanguage('zh-CN');
			forgiveSelect();
		}
	} else if (key == "new_conversation") {
		appStore.setConversationID(key);
		forgiveSelect();
	} else {
		appStore.setConversationID(key)
	}
}

const forgiveSelect = () => {
	currentConversationId.value = null
}

watch(() => appStore.conversation_id, (newVal, oldVal) => {
	currentConversationId.value = newVal
});

const languageOptions = [
	{
		label: '简体中文',
		key: 'zh-CN',
		props: {
			onClick: () => {
				appStore.setLanguage('zh_CN');
			}
		}
	},
	{
		label: 'English',
		key: 'en-US',
		props: {
			onClick: () => {
				appStore.setLanguage('en_US');
			}
		}
	}
]
const helpOptions1 = [
	{
		label: t("commons.helpDoc"),
		key: 'helpDoc',
		props: {
			onClick: () => {
				let newPage = window.open('/chatbgi_v2.html');
				setTimeout(() => {
					if (newPage !== null) {
						newPage.document.title = 'ChatBGI帮助文档'
					}
				}, 500)
			}
		}
	},
	{
		label: t("commons.firstLogin"),
		key: 'control',
		props: {
			onClick: () => {
				userStore.$state.isFirstLogin = true;
			}
		}
	}
]
const helpOptions2 = [
	{
		label: '查看帮助文档',
		key: 'helpDoc',
		props: {
			onClick: () => {
				let newPage = window.open('../../public/chatbgi_v2.html');
				setTimeout(() => {
					if (newPage !== null) {
						newPage.document.title = 'ChatBGI帮助文档'
					}
				}, 50)
			}
		}
	}
]
const sleep = () => new Promise((resolve) => setTimeout(resolve, 1000))
const options = ref<Array<DropdownOption>>([
	{
		label: t("commons.resetPassword"),
		key: 'resetPassword',
		props: {
			onClick: () => {
				popupResetUserPasswordDialog(
					async (oldpassword: string, newpassword: string) => {
						await resetUserPasswordApi(userStore.user!.id, oldpassword, newpassword);
					},
					() => { Message.info(t("tips.resetUserPasswordSuccess")) },
					() => { Message.error(t("tips.resetUserPasswordFailed")) }
				)
			}
		}
	},
	{
		label: t("commons.userProfile"),
		key: 'profile',
		props: {
			onClick: () => Dialog.info({
				title: t("commons.userProfile"),
				content: () => h(UserProfileCard, {}, {}),
				// positiveText: t("commons.edit"),
				negativeText: t("commons.close"),
				// onPositiveClick: () => { router.push({ name: 'editProfile' }) }
			})
		}
	},
	// {
	//   label: t("commons.translatePPT"),
	//   key: 'translate',
	//   props: {
	//     onClick: () => {
	//       const a = Dialog.info({
	//         title: t("commons.translatePPTto"),
	//         icon: () => h(NIcon, null, { default: () => h(BookOutline) }),
	//         content: () => h(TranslatePPT, { ref: childRef }, {}),
	//         positiveText: t("commons.submit"),
	//         onPositiveClick: () => {
	//           a.loading = true
	//           childRef.value.uploadFile()
	//           return new Promise((resolve) => {
	//             setTimeout(resolve, 3000)
	//           })
	//         },
	//       })
	//     }
	//   }
	// },
	{
		label: t("commons.logout"),
		key: 'logout',
		props: {
			onClick: () => Dialog.info({
				title: t("commons.logout"),
				content: t("tips.logoutConfirm"),
				positiveText: t("commons.confirm"),
				negativeText: t("commons.cancel"),
				onPositiveClick: () => userStore.logout().then(() => {
					Message.success(t('commons.logoutSuccess'));
					router.push({ name: 'login' });
				}),
			})
		}
	}
])

const jumpToAdminOrConv = async () => {
	if (isInAdmin.value) {
		await router.push({ name: 'conversation' });
	} else {
		await router.push({ name: 'admin' });
	}
};

</script>
