import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WorkinDaysSettingDetailComponent } from './workin-days-setting-detail.component';

describe('WorkinDaysSetting Management Detail Component', () => {
  let comp: WorkinDaysSettingDetailComponent;
  let fixture: ComponentFixture<WorkinDaysSettingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkinDaysSettingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ workinDaysSetting: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(WorkinDaysSettingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WorkinDaysSettingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load workinDaysSetting on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.workinDaysSetting).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
