import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfListComponent } from './prof-list.component';

describe('ProfListComponent', () => {
  let component: ProfListComponent;
  let fixture: ComponentFixture<ProfListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
