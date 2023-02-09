import dayjs from 'dayjs/esm';

export interface IEducation {
  id: number;
  institution?: string | null;
  subject?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  educationType?: string | null;
  grade?: string | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  employeeId?: number | null;
  companyId?: number | null;
}

export type NewEducation = Omit<IEducation, 'id'> & { id: null };
