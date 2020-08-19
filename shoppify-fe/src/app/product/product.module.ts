import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductRoutingModule } from './product-routing.module';
import { CoreModule } from '../core/core.module';
import { SharedModule } from '../shared/shared.module';
import { SingleProductComponent } from './components/single-product/single-product.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { UploadPhotoComponent } from './components/upload-photo/upload-photo.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddItemComponent } from './components/add-item/add-item.component';

@NgModule({
	declarations: [ SingleProductComponent, AddProductComponent, AddItemComponent, UploadPhotoComponent ],
	imports: [ CommonModule, CoreModule, SharedModule, ProductRoutingModule, FormsModule, ReactiveFormsModule ]
})
export class ProductModule {}
