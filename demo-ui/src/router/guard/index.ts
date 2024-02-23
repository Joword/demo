import type { Router } from 'vue-router';
import setupUserLoginInfoGuard from './loginInfo';
import setupPermissionGuard from './permission';

export default function createRouteGuard(router: Router) {
	setupUserLoginInfoGuard(router);
	setupPermissionGuard(router);
}
