import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { PeliDetallePage } from './peli-detalle.page';

describe('PeliDetallePage', () => {
  let component: PeliDetallePage;
  let fixture: ComponentFixture<PeliDetallePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PeliDetallePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(PeliDetallePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
