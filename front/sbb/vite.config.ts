import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vitest/config';

export default defineConfig({
	plugins: [sveltekit()],
	test: {
		include: ['src/**/*.{test,spec}.{js,ts}']
	},
	server: {
		proxy: {
			"/sbb/": {
				target: "http://localhost:8080",
				rewrite: (path) => path.replace(/^\/sbb/, ""),
			},
		},
	},
});
