import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BeneficioService, Beneficio } from '../../services/beneficio.service';

@Component({
  selector: 'app-beneficio-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficio-form.component.html',
  styleUrls: ['./beneficio-form.component.css']
})
export class BeneficioFormComponent implements OnInit {
  beneficio: Beneficio = { nome: '', descricao: '', valor: 0, ativo: true };
  id?: number;

  constructor(
    private service: BeneficioService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.id) {
      this.service.get(this.id).subscribe((b: Beneficio) => (this.beneficio = b));
    }
  }

  save(): void {
    if (this.id) {
      this.service.update(this.id, this.beneficio).subscribe(() => this.router.navigate(['/beneficios']));
    } else {
      this.service.create(this.beneficio).subscribe(() => this.router.navigate(['/beneficios']));
    }
  }

  cancel(): void {
    this.router.navigate(['/beneficios']);
  }
}
