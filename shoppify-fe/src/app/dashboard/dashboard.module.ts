import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WelcomeSideComponent } from './components/partials/welcome-side/welcome-side.component';
import { AuthenticationRoutingModule } from '../authentication/authentication-routing.module';
import { HomeComponent } from './components/home/home.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { FormsModule } from '@angular/forms';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { CoreModule } from '../core/core.module';

@NgModule({
	declarations: [ WelcomeSideComponent, HomeComponent, ProductCardComponent, ShoppingCartComponent ],
	imports: [ CommonModule, AuthenticationRoutingModule, DashboardRoutingModule, SharedModule, FormsModule, CoreModule ],
	exports: [ WelcomeSideComponent ]
})
export class DashboardModule {}
