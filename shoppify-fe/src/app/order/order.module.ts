import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderRoutingModule } from './order-routing.module';
import { OrdersComponent } from './components/orders/orders.component';
import { SharedModule } from '../shared/shared.module';
import { CoreModule } from '../core/core.module';

@NgModule({
	declarations: [ OrdersComponent ],
	imports: [ CommonModule, OrderRoutingModule, SharedModule, CoreModule ]
})
export class OrderModule {}
