import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BeneficioService, Beneficio } from '../../services/beneficio.service';

@Component({
  selector: 'app-beneficio-transfer',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficio-transfer.component.html',
  styleUrls: ['./beneficio-transfer.component.css']
})
export class BeneficioTransferComponent implements OnInit {
  beneficios: Beneficio[] = [];
  origemId?: number;
  destinoId?: number;
  valor: number = 0;

  constructor(private service: BeneficioService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.service.list().subscribe((b: Beneficio[]) => (this.beneficios = b));
    this.origemId = Number(this.route.snapshot.paramMap.get('id'));
  }

  transfer(): void {
    if (!this.origemId || !this.destinoId || this.valor <= 0) {
      alert('Preencha origem, destino e valor válido.');
      return;
    }
    this.service.transfer(this.origemId, this.destinoId, this.valor).subscribe({
      next: () => {
        alert('Transferência realizada com sucesso');
        this.router.navigate(['/beneficios']);
      },
      error: (err) => {
        alert('Erro: ' + (err?.error || err?.message || err));
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/beneficios']);
  }
}
