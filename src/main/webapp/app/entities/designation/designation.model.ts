import dayjs from 'dayjs/esm';

export interface IDesignation {
  id: number;
  name?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  departmentId?: number | null;
  companyId?: number | null;
}

export type NewDesignation = Omit<IDesignation, 'id'> & { id: null };
