import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationComponent } from './components/navigation/navigation.component';
import { RouterModule } from '@angular/router';
import { ContainerComponent } from './components/common/container/container.component';
import { CoreModule } from '../core/core.module';

@NgModule({
	declarations: [ NavigationComponent, ContainerComponent ],
	imports: [ CommonModule, RouterModule, CoreModule ],
	exports: [ NavigationComponent, ContainerComponent ]
})
export class SharedModule {}
