import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BeneficioListComponent } from './components/beneficio-list/beneficio-list.component';
import { BeneficioFormComponent } from './components/beneficio-form/beneficio-form.component';
import { BeneficioTransferComponent } from './components/beneficio-transfer/beneficio-transfer.component';


@NgModule({
  imports: [
    CommonModule,
    BeneficioListComponent,
    BeneficioFormComponent,
    BeneficioTransferComponent
  ],
  exports: [
    BeneficioListComponent,
    BeneficioFormComponent,
    BeneficioTransferComponent
  ]
})
export class BeneficioModule {}
