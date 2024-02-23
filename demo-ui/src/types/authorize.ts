export interface modeType {
	color: string;
	status: boolean|null;
	isDisabled: boolean|null;
}


export interface authorize {
	isAuthorize: {
		is_verified?: boolean;
		user_id: Array<string>;
		user_ids: any;
		mode?: string;
		emails: any;
	};
}