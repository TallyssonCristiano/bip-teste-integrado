import { Routes } from '@angular/router';
import { BeneficioListComponent } from './beneficio-list/beneficio-list.component';
import { BeneficioFormComponent } from './beneficio-form/beneficio-form.component';
import { BeneficioTransferComponent } from './beneficio-transfer/beneficio-transfer.component';

export const routes: Routes = [
  { path: 'beneficios', component: BeneficioListComponent },
  { path: 'beneficios/criar', component: BeneficioFormComponent },
  { path: 'beneficios/editar/:id', component: BeneficioFormComponent },
  { path: 'beneficios/transferir/:id', component: BeneficioTransferComponent },
  { path: '', redirectTo: 'beneficios', pathMatch: 'full' }
];
