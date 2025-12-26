import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { BeneficioService, Beneficio } from './beneficio.service';

describe('BeneficioService', () => {
  let service: BeneficioService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),       
        provideHttpClientTesting(), 
        BeneficioService
      ]
    });
    service = TestBed.inject(BeneficioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch list of beneficios', () => {
    const mockBeneficios: Beneficio[] = [
      { id: 1, nome: 'Teste', descricao: 'Desc', valor: 100, ativo: true }
    ];

    service.list().subscribe(b => expect(b.length).toBe(1));

    // usar service['apiUrl'] se apiUrl for private
    const req = httpMock.expectOne(service['apiUrl']);
    expect(req.request.method).toBe('GET');
    req.flush(mockBeneficios);
  });

  it('should call transfer endpoint', () => {
    service.transfer(1, 2, 50).subscribe();

    const req = httpMock.expectOne(`${service['apiUrl']}/transfer?fromId=1&toId=2&amount=50`);
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });
});
