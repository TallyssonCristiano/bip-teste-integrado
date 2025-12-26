import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Beneficio {
  id?: number;
  nome: string;
  descricao?: string;
  valor: number;
  ativo: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {
  private apiUrl = 'http://localhost:8080/api/v1/beneficios';

  constructor(private http: HttpClient) {}

  list(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.apiUrl);
  }

  get(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.apiUrl}/${id}`);
  }

  create(beneficio: Beneficio): Observable<Beneficio> {
    return this.http.post<Beneficio>(this.apiUrl, beneficio);
  }

  update(id: number, beneficio: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${this.apiUrl}/${id}`, beneficio);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  transfer(fromId: number, toId: number, amount: number): Observable<void> {
    const params = new HttpParams()
      .set('origemId', String(fromId))
      .set('destinoId', String(toId))
      .set('valor', String(amount));

    return this.http.post<void>(`${this.apiUrl}/transfer`, null, { params });
  }
}
