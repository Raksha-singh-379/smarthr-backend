import dayjs from 'dayjs/esm';

export interface IWorkExperience {
  id: number;
  jobTitle?: string | null;
  companyName?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  addressId?: number | null;
  employeeId?: number | null;
  companyId?: number | null;
  yearOfExp?: number | null;
  jobDesc?: string | null;
}

export type NewWorkExperience = Omit<IWorkExperience, 'id'> & { id: null };
