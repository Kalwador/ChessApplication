import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamePveListComponent } from './game-pve-list.component';

describe('GamePveListComponent', () => {
  let component: GamePveListComponent;
  let fixture: ComponentFixture<GamePveListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamePveListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamePveListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
