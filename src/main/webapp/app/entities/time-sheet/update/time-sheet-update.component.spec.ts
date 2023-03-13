import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TimeSheetFormService } from './time-sheet-form.service';
import { TimeSheetService } from '../service/time-sheet.service';
import { ITimeSheet } from '../time-sheet.model';
import { IAttendance } from 'app/entities/attendance/attendance.model';
import { AttendanceService } from 'app/entities/attendance/service/attendance.service';

import { TimeSheetUpdateComponent } from './time-sheet-update.component';

describe('TimeSheet Management Update Component', () => {
  let comp: TimeSheetUpdateComponent;
  let fixture: ComponentFixture<TimeSheetUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let timeSheetFormService: TimeSheetFormService;
  let timeSheetService: TimeSheetService;
  let attendanceService: AttendanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TimeSheetUpdateComponent],
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
      .overrideTemplate(TimeSheetUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TimeSheetUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    timeSheetFormService = TestBed.inject(TimeSheetFormService);
    timeSheetService = TestBed.inject(TimeSheetService);
    attendanceService = TestBed.inject(AttendanceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Attendance query and add missing value', () => {
      const timeSheet: ITimeSheet = { id: 456 };
      const attendance: IAttendance = { id: 64676 };
      timeSheet.attendance = attendance;

      const attendanceCollection: IAttendance[] = [{ id: 44772 }];
      jest.spyOn(attendanceService, 'query').mockReturnValue(of(new HttpResponse({ body: attendanceCollection })));
      const additionalAttendances = [attendance];
      const expectedCollection: IAttendance[] = [...additionalAttendances, ...attendanceCollection];
      jest.spyOn(attendanceService, 'addAttendanceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      expect(attendanceService.query).toHaveBeenCalled();
      expect(attendanceService.addAttendanceToCollectionIfMissing).toHaveBeenCalledWith(
        attendanceCollection,
        ...additionalAttendances.map(expect.objectContaining)
      );
      expect(comp.attendancesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const timeSheet: ITimeSheet = { id: 456 };
      const attendance: IAttendance = { id: 63464 };
      timeSheet.attendance = attendance;

      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      expect(comp.attendancesSharedCollection).toContain(attendance);
      expect(comp.timeSheet).toEqual(timeSheet);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimeSheet>>();
      const timeSheet = { id: 123 };
      jest.spyOn(timeSheetFormService, 'getTimeSheet').mockReturnValue(timeSheet);
      jest.spyOn(timeSheetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timeSheet }));
      saveSubject.complete();

      // THEN
      expect(timeSheetFormService.getTimeSheet).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(timeSheetService.update).toHaveBeenCalledWith(expect.objectContaining(timeSheet));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimeSheet>>();
      const timeSheet = { id: 123 };
      jest.spyOn(timeSheetFormService, 'getTimeSheet').mockReturnValue({ id: null });
      jest.spyOn(timeSheetService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSheet: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timeSheet }));
      saveSubject.complete();

      // THEN
      expect(timeSheetFormService.getTimeSheet).toHaveBeenCalled();
      expect(timeSheetService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimeSheet>>();
      const timeSheet = { id: 123 };
      jest.spyOn(timeSheetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(timeSheetService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAttendance', () => {
      it('Should forward to attendanceService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(attendanceService, 'compareAttendance');
        comp.compareAttendance(entity, entity2);
        expect(attendanceService.compareAttendance).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
