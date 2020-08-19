import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SingleProductComponent } from './components/single-product/single-product.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { AddItemComponent } from './components/add-item/add-item.component';
import { UploadPhotoComponent } from './components/upload-photo/upload-photo.component';
import { AdminGuard } from '../core/guards/admin.guard';

const routes: Routes = [
	{ path: 'single/:id', component: SingleProductComponent },
	{
		path: 'add-product',
		component: AddProductComponent,
		canActivate: [ AdminGuard ]
	},
	{
		path: 'add-product/:item',
		component: AddItemComponent,
		canActivate: [ AdminGuard ]
	},
	{
		path: 'photos/:id',
		component: UploadPhotoComponent,
		canActivate: [ AdminGuard ]
	}
];

@NgModule({
	imports: [ RouterModule.forChild(routes) ],
	exports: [ RouterModule ]
})
export class ProductRoutingModule {}
