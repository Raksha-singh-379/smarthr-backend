import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReportingStructureFormService } from './reporting-structure-form.service';
import { ReportingStructureService } from '../service/reporting-structure.service';
import { IReportingStructure } from '../reporting-structure.model';

import { ReportingStructureUpdateComponent } from './reporting-structure-update.component';

describe('ReportingStructure Management Update Component', () => {
  let comp: ReportingStructureUpdateComponent;
  let fixture: ComponentFixture<ReportingStructureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reportingStructureFormService: ReportingStructureFormService;
  let reportingStructureService: ReportingStructureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReportingStructureUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ReportingStructureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportingStructureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reportingStructureFormService = TestBed.inject(ReportingStructureFormService);
    reportingStructureService = TestBed.inject(ReportingStructureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const reportingStructure: IReportingStructure = { id: 456 };

      activatedRoute.data = of({ reportingStructure });
      comp.ngOnInit();

      expect(comp.reportingStructure).toEqual(reportingStructure);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportingStructure>>();
      const reportingStructure = { id: 123 };
      jest.spyOn(reportingStructureFormService, 'getReportingStructure').mockReturnValue(reportingStructure);
      jest.spyOn(reportingStructureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportingStructure });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportingStructure }));
      saveSubject.complete();

      // THEN
      expect(reportingStructureFormService.getReportingStructure).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reportingStructureService.update).toHaveBeenCalledWith(expect.objectContaining(reportingStructure));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportingStructure>>();
      const reportingStructure = { id: 123 };
      jest.spyOn(reportingStructureFormService, 'getReportingStructure').mockReturnValue({ id: null });
      jest.spyOn(reportingStructureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportingStructure: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reportingStructure }));
      saveSubject.complete();

      // THEN
      expect(reportingStructureFormService.getReportingStructure).toHaveBeenCalled();
      expect(reportingStructureService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReportingStructure>>();
      const reportingStructure = { id: 123 };
      jest.spyOn(reportingStructureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reportingStructure });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reportingStructureService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
