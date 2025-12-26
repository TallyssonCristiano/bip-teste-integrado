import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './modules/beneficio/components/app-routing.module';

@NgModule({
  imports: [
    BrowserModule
  ],
  providers: [
    provideHttpClient(),
    provideRouter(routes)
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
