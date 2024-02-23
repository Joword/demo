import { UserRead } from "@/types/schema";

interface UserState {
	user: UserRead | null;
	savedUsername: string | null;
	savedPassword: string | null;
	isFirstLogin?: boolean;
}

interface AppState {
	theme: any;
	language: any;
	conversation_id: string | null;
}

export type { UserState, AppState };
