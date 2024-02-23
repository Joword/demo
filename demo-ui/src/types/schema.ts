import { authorize } from './authorize';
import { UserCreate } from '../../.history/src/types/schema_20240223140915';

export type isAuthorize = authorize["isAuthorize"];

export type UserRead = {
	title: String|null
	required: Array<String>|null
	type: String|null
	properties: Array<String>|null
	email: Object|null
	is_active: Object|null
	is_superuser: Object|null
	is_verified: Object|null
	username: Object|null
	nickname: Object|null
	active_time: Object|null
	chat_status: Object|null
	description: String|null
}

export type Conversation ={
	title: String|null
	type: String|null
	properites: Object|null
	conversation_id: Object|null
	user_id: Object|null
	create_time: Object|null
	active_time: Object|null
	is_valid: Object|null
}

export type UserCreate = {
	username: String|null
	email: String|null
	nickname: String|null
	requestcode: String|null
	password: String|null
	organization: String|null
	is_superuser: Boolean|null
}

export const chatStatusMap = {
	asking: "commons.askingChatStatus",
	queueing: "commons.queueingChatStatus",
	idling: "commons.idlingChatStatus",
};
