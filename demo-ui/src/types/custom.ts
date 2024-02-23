import { UserRead } from "./schema";

export type ChatMessage = {
	id: string;
	author_role: "user" | "assistant" | string;
	model?: string;
	message?: string;
	reference?: string;
	parent?: string;
	children: Array<string>;
	typing?: boolean;
	withdrawMessagebutton?: boolean;
};

export type ChatConversationDetail = {
	id: string;
	current_node: string;
	title: string;
	create_time: number;
	mapping: Record<string, ChatMessage>;
};

export type tableData = {
	origin: Array<UserRead> | null ;
	show: Array<UserRead> | null ;
}
