import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { BeneficioService, Beneficio } from '../../services/beneficio.service';

@Component({
  selector: 'app-beneficio-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './beneficio-list.component.html',
  styleUrls: ['./beneficio-list.component.css']
})
export class BeneficioListComponent implements OnInit {
  beneficios: Beneficio[] = [];

  constructor(private service: BeneficioService, private router: Router) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.service.list().subscribe({
      next: (b: Beneficio[]) => {
        console.log('Beneficios recebidos:', b);
        this.beneficios = b;
      },
      error: (err) => console.error('Erro ao carregar beneficios', err)
    });
  }

  create(): void {
    this.router.navigate(['/beneficios/criar']);
  }

  edit(id: number): void {
    this.router.navigate(['/beneficios/editar', id]);
  }

  remove(id: number): void {
    if (!confirm('Confirma remoção do benefício?')) return;
    this.service.delete(id).subscribe(() => this.load());
  }

  transfer(id: number): void {
    this.router.navigate(['/beneficios/transferir', id]);
  }
}
