import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
	{
		path: '',
		loadChildren: './authentication/authentication.module#AuthenticationModule'
	},
	{
		path: 'home',
		loadChildren: './dashboard/dashboard.module#DashboardModule'
	},
	{
		path: 'product',
		loadChildren: './product/product.module#ProductModule'
	},
	{
		path: 'order',
		loadChildren: './order/order.module#OrderModule'
	},
	{ path: '**', redirectTo: 'page-not-found', pathMatch: 'full' }
];

@NgModule({
	imports: [ RouterModule.forRoot(routes, { useHash: true }) ],
	exports: [ RouterModule ]
})
export class AppRoutingModule {}
