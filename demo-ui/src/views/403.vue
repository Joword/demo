<template>
	<!-- 403 error page -->
	<div class="h-screen flex flex-col mx-2 sm:mx-4">
		<div class="flex-1 flex justify-center items-center">
			<n-card class="h-full mb-6 flex justify-center items-center">
				<div class="text-2xl text-gray-500">
					{{ $t("errors.403") }}
				</div>
			</n-card>
		</div>
	</div>
</template>
	